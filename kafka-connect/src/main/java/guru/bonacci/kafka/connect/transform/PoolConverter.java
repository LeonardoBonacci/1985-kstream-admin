package guru.bonacci.kafka.connect.transform;

import static org.apache.kafka.connect.transforms.util.Requirements.requireStruct;

import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.transforms.Transformation;


public class PoolConverter<R extends ConnectRecord<R>> implements Transformation<R> {

  
  private static final String PURPOSE = "transform into ledger-pool";

  @Override
  public void configure(Map<String, ?> props) {
  }


  @Override
  public R apply(R record) {
    if (operatingSchema(record) == null) {
      return applySchemaless(record);
    } else {
      return applyWithSchema(record);
    }
  }

  R applySchemaless(R record) {
    throw new UnsupportedOperationException();
  }

  R applyWithSchema(R record) {
    if (record.value() == null) {
      return record; //tombstone
    }

    final Struct value = requireStruct(operatingValue(record), PURPOSE);
    if (!value.getBoolean("active")) { // continue as tombstone
      return newRecord(record, value.get("name"), null, null);
    }

    final Schema poolSchema = 
        SchemaBuilder.struct().field("poolId", Schema.STRING_SCHEMA)
                              .field("type", Schema.STRING_SCHEMA).build();

    String poolId = value.getString("name");
    String type = value.getString("type");

    final Struct pool = new Struct(poolSchema).put("poolId", poolId).put("type", type);
    return newRecord(record, poolId, poolSchema, pool);
  }

  Schema operatingSchema(R record) {
    return record.valueSchema();
  }

  Object operatingValue(R record) {
    return record.value();
  }

  R newRecord(R record, Object updatedKey, Schema updatedSchema, Object updatedValue) {
    return record.newRecord(record.topic(), record.kafkaPartition(), null, updatedKey, updatedSchema, updatedValue, record.timestamp());
  }

  @Override
  public ConfigDef config() {
    return new ConfigDef();
  }

  @Override
  public void close() {
  }
}


