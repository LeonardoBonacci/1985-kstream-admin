package guru.bonacci.kafka.connect.transform;

import static org.apache.kafka.connect.transforms.util.Requirements.requireStruct;

import java.util.Map;
import java.util.logging.Logger;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.errors.DataException;
import org.apache.kafka.connect.transforms.Transformation;
import org.apache.kafka.connect.transforms.util.SimpleConfig;


public abstract class CreateTombstone<R extends ConnectRecord<R>> implements Transformation<R> {

  private Logger log = Logger.getLogger(CreateTombstone.class.getName());
  
  
  public static final String OVERVIEW_DOC =
      "Might create tombstone record based on predicate";


  public static final ConfigDef CONFIG_DEF = new ConfigDef()
      .define(ConfigName.TOMBSTONE_FIELD_NAME, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, "Field name for tombstone predicate")
      .define(ConfigName.TOMBSTONE_FIELD_VALUE, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, "Field value for tombstone predicate")
      ;

  
  private interface ConfigName {
    String TOMBSTONE_FIELD_NAME = "tombstone.field.name";
    String TOMBSTONE_FIELD_VALUE = "tombstone.field.value";
  }
  

  private String fieldName;
  private String fieldValue;
  
  private static final String PURPOSE = "maybe tombstone";

  
  @Override
  public void configure(Map<String, ?> props) {
    final SimpleConfig config = new SimpleConfig(CONFIG_DEF, props);
    fieldName = config.getString(ConfigName.TOMBSTONE_FIELD_NAME);
    fieldValue = config.getString(ConfigName.TOMBSTONE_FIELD_VALUE);
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
    try {
      Object value = payloadWithSchema.get(fieldName);

      if (value == null) {
        log.warning("empty value for configured field " + fieldName);
     
      } else if (value.equals(fieldValue)) {
        payloadWithSchema = null;
      }
    } catch (DataException de) {
      log.warning("no such field " + fieldName);
    }

    return newRecord(record, payloadWithSchema != null ? payloadWithSchema.schema() : null , payloadWithSchema);
  }
  
  @Override
  public ConfigDef config() {
    return CONFIG_DEF;
  }

  @Override
  public void close() {
  }

  protected abstract Schema operatingSchema(R record);

  protected abstract Object operatingValue(R record);

  protected abstract R newRecord(R record, Schema updatedSchema, Object updatedValue);

  public static class Value<R extends ConnectRecord<R>> extends CreateTombstone<R> {

    @Override
    protected Schema operatingSchema(R record) {
      return record.valueSchema();
    }

    @Override
    protected Object operatingValue(R record) {
      return record.value();
    }

    @Override
    protected R newRecord(R record, Schema updatedSchema, Object updatedValue) {
      return record.newRecord(record.topic(), record.kafkaPartition(), record.keySchema(), record.key(), updatedSchema, updatedValue, record.timestamp());
    }
  }
}


