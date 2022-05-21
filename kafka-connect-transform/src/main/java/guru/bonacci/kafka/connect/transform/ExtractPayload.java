package guru.bonacci.kafka.connect.transform;

import static org.apache.kafka.connect.transforms.util.Requirements.requireStruct;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.transforms.Transformation;

import com.google.gson.Gson;

public abstract class ExtractPayload<R extends ConnectRecord<R>> implements Transformation<R> {

  public static final String OVERVIEW_DOC = "Get rid of that schema";

  private static final String PURPOSE = "extracting payload and leaving schema behind";

  private Gson gson = new Gson();

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

  private R applySchemaless(R record) {
    return record;
  }

  private R applyWithSchema(R record) {
    if (record.value() == null) {
      return record; //tombstone
    }
    
    Struct payloadWithSchema = requireStruct(operatingValue(record), PURPOSE);
    Map<Object, Object> payload = extractPayload(payloadWithSchema);
    String json = gson.toJson(payload); 

    return newRecord(record, json);
  }

  private Map<Object, Object> extractPayload(Struct struct) { 
    final Map<Object, Object> payload = new HashMap<>();

    for (Field field : struct.schema().fields()) {
      Object value = struct.get(field);
      if (field.schema().type() == Schema.Type.STRUCT) {
        payload.put(field.name(), extractPayload((Struct)value));
      } else { 
        payload.put(field.name(), value);
      }
    }  
    return payload;
  }  
  
  @Override
  public ConfigDef config() {
    return new ConfigDef();
  }

  @Override
  public void close() {
  }

  protected abstract Schema operatingSchema(R record);

  protected abstract Object operatingValue(R record);

  protected abstract R newRecord(R record, Object updatedValue);

  public static class Key<R extends ConnectRecord<R>> extends ExtractPayload<R> {

    @Override
    protected Schema operatingSchema(R record) {
      return record.keySchema();
    }

    @Override
    protected Object operatingValue(R record) {
      return record.key();
    }

    @Override
    protected R newRecord(R record, Object updatedValue) {
      return record.newRecord(record.topic(), record.kafkaPartition(), null, updatedValue, record.valueSchema(), record.value(), record.timestamp());
    }

  }

  public static class Value<R extends ConnectRecord<R>> extends ExtractPayload<R> {

    @Override
    protected Schema operatingSchema(R record) {
      return record.valueSchema();
    }

    @Override
    protected Object operatingValue(R record) {
      return record.value();
    }

    @Override
    protected R newRecord(R record, Object updatedValue) {
      return record.newRecord(record.topic(), record.kafkaPartition(), record.keySchema(), record.key(), null, updatedValue, record.timestamp());
    }
  }
}


