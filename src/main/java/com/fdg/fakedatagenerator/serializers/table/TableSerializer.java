package com.fdg.fakedatagenerator.serializers.table;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fdg.fakedatagenerator.table.Table;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TableSerializer extends StdSerializer<Table> {

  public TableSerializer() {
    this(null);
  }

  protected TableSerializer(Class<Table> t) {
    super(t);
  }

  // TODO: Standardize these field names for the yml file

  @Override
  public void serialize(
      Table table, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    log.info("Serializing table: " + table.toString());
    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("name", table.getName());
    jsonGenerator.writeObjectField("schema", table.getSchema());
    jsonGenerator.writeEndObject();
  }
}
