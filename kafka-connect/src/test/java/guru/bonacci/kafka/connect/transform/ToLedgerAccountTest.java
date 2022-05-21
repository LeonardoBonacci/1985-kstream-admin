package guru.bonacci.kafka.connect.transform;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.junit.After;

public class ToLedgerAccountTest {

  private ToLedgerAccount<SourceRecord> smt = new ToLedgerAccount.Value<>();

  @After
  public void tearDown() throws Exception {
    smt.close();
  }

//  @Test
  public void schema() {
    final Schema simpleStructSchema = SchemaBuilder.struct().name("name").version(1).doc("doc").field("magic", Schema.OPTIONAL_INT64_SCHEMA).build();
    final Struct simpleStruct = new Struct(simpleStructSchema).put("magic", 42L);

    final SourceRecord record = new SourceRecord(null, null, "test", 0, simpleStructSchema, simpleStruct);
    final SourceRecord transformedRecord = smt.apply(record);

    System.out.println(transformedRecord.toString());
  }

//  @Test
  public void nesting() {
    final Schema innerStructSchema = SchemaBuilder.struct().name("name").version(1).doc("doc").field("magic", Schema.OPTIONAL_INT64_SCHEMA).build();
    final Struct innerStruct = new Struct(innerStructSchema).put("magic", 42L);

    final Schema outerStructSchema = SchemaBuilder.struct().name("name").version(1).doc("doc").field("foo", innerStructSchema).build();
    final Struct outerStruct = new Struct(outerStructSchema).put("foo", innerStruct);

    final SourceRecord record = new SourceRecord(null, null, "test", 0, outerStructSchema, outerStruct);
    final SourceRecord transformedRecord = smt.apply(record);

    System.out.println(transformedRecord.toString());
  }

//  @Test
  public void schemaless() {
    final SourceRecord record = new SourceRecord(null, null, "test", 0,
      null, Collections.singletonMap("magic", 42L));

    final SourceRecord transformedRecord = smt.apply(record);
    assertEquals(record, transformedRecord);
  }
}