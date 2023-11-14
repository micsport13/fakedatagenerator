package com.fdg.fakedatagenerator.serializers.table;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fdg.fakedatagenerator.serializers.column.ColumnSerializer;
import com.fdg.fakedatagenerator.table.Table;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class TableSerializer extends StdSerializer<Table> {
  // TODO: Standardize these field names for the yml file
  public TableSerializer() {
    super(Table.class, false);
  }

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
