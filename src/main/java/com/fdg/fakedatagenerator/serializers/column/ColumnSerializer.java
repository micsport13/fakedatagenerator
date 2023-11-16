package com.fdg.fakedatagenerator.serializers.column;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fdg.fakedatagenerator.column.Column;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ColumnSerializer extends JsonSerializer<Column<?>> {
  // TODO: Standardize these field names for the yml file
  public ColumnSerializer() {
    this(null);
  }

  protected ColumnSerializer(Class<Column<?>> t) {
    super();
  }

  @Override
  public void serialize(
      Column column, JsonGenerator ymlGenerator, SerializerProvider serializerProvider)
      throws IOException {
    log.info("Serializing column: " + column);
    ymlGenerator.writeStartObject();
    ymlGenerator.writeStringField("name", column.getName());
    ymlGenerator.writeObjectField("type",column.getDataType());
    if (!column.getConstraints().isEmpty()) {
      ymlGenerator.writeArrayFieldStart("constraints");
      for (var columnConstraint : column.getConstraints()) {
        ymlGenerator.writeObject(columnConstraint);
      }
      ymlGenerator.writeEndArray();
    }
    ymlGenerator.writeEndObject();
  }
}
