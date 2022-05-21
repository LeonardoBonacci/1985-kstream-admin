package guru.bonacci.kafka.connect.transform;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.junit.After;
import org.junit.Test;

public class CreateTombstoneTest {

  private CreateTombstone<SourceRecord> smt = new CreateTombstone.Value<>();

  @After
  public void tearDown() throws Exception {
    smt.close();
  }

  @Test
  public void tombstone() {
    Map<String, String> config = new HashMap<>();
    config.put("tombstone.field.name", "foo");
    config.put("tombstone.field.value", "fooValue");
    smt.configure(config);

    final Schema simpleStructSchema = SchemaBuilder.struct().name("name").version(1).doc("doc").field("foo", Schema.STRING_SCHEMA).build();
    final Struct simpleStruct = new Struct(simpleStructSchema).put("foo", "fooValue");

    final SourceRecord record = new SourceRecord(null, null, "test", 0, simpleStructSchema, simpleStruct);
    final SourceRecord transformedRecord = smt.apply(record);

    System.out.println(transformedRecord.toString());
  }

  @Test
  public void notombstone() {
    final Schema simpleStructSchema = SchemaBuilder.struct().name("name").version(1).doc("doc").field("magic", Schema.OPTIONAL_INT64_SCHEMA).build();
    final Struct simpleStruct = new Struct(simpleStructSchema).put("magic", 42L);

    final SourceRecord record = new SourceRecord(null, null, "test", 0, simpleStructSchema, simpleStruct);
    final SourceRecord transformedRecord = smt.apply(record);

    System.out.println(transformedRecord.toString());
  }

  @Test
  public void schemaless() {
    final SourceRecord record = new SourceRecord(null, null, "test", 0,
      null, Collections.singletonMap("magic", 42L));

    final SourceRecord transformedRecord = smt.apply(record);
    assertEquals(record, transformedRecord);
  }
}