package com.fakedatagenerator.serializers;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.schema.Schema;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SchemaSerializer extends StdSerializer<Schema> {
  public SchemaSerializer() {
    this(null);
  }

  protected SchemaSerializer(Class<Schema> t) {
    super(t);
  }

  @Override
  public void serialize( // TODO: Is there a way to make this follow OCP?
      Schema schema, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    log.info("Serializing schema: " + schema.toString());
    jsonGenerator.writeStartObject();
    jsonGenerator.writeArrayFieldStart("columns");
    for (var column : schema.getColumns()) {
      jsonGenerator.writeObject(column);
    }
    jsonGenerator.writeEndArray();
    jsonGenerator.writeArrayFieldStart("constraints");
    for (var constraint : schema.getConstraints().entrySet()) {
      jsonGenerator.writeStartObject();
      jsonGenerator.writeObjectField("constraint", constraint.getKey());
      jsonGenerator.writeArrayFieldStart("columns");
      for (Column<?> column : constraint.getValue()) {
        jsonGenerator.writeString(column.getName());
      }
      jsonGenerator.writeEndArray();
      jsonGenerator.writeEndObject();
    }
    jsonGenerator.writeEndArray();
    jsonGenerator.writeEndObject();
  }
}
