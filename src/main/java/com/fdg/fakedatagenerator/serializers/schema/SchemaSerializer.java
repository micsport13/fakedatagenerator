package com.fdg.fakedatagenerator.serializers.schema;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fdg.fakedatagenerator.schema.Schema;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SchemaSerializer extends StdSerializer<Schema> {
  // TODO: Standardize these field names for the yml file
  public SchemaSerializer() {
    this(null);
  }

  protected SchemaSerializer(Class<Schema> t) {
    super(t);
  }

  @Override
  public void serialize(
      Schema schema, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    log.info("Serializing schema: " + schema.toString());
    jsonGenerator.writeStartObject();
    jsonGenerator.writeArrayFieldStart("columns");
    for (var column : schema.getColumns()) {
      jsonGenerator.writeStartObject();
      jsonGenerator.writeObjectField("column", column);
      if (!schema.getTableConstraints().get(column).isEmpty()) {
        jsonGenerator.writeArrayFieldStart("table_constraints");
        for (var tableConstraint : schema.getTableConstraints().get(column)) {
          jsonGenerator.writeObject(tableConstraint);
        }
        jsonGenerator.writeEndArray();
      }
      jsonGenerator.writeEndObject();
    }
    jsonGenerator.writeEndArray();
    jsonGenerator.writeEndObject();
  }
}
