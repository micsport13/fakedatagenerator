package com.fdg.fakedatagenerator.serializers.constraints.column;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fdg.fakedatagenerator.constraints.column.ColumnCheckConstraint;
import java.io.IOException;

public class ColumnCheckConstraintSerializer extends StdSerializer<ColumnCheckConstraint> {

  public ColumnCheckConstraintSerializer() {
    this(null);
  }

  protected ColumnCheckConstraintSerializer(Class<ColumnCheckConstraint> t) {
    super(t);
  }

  @Override
  public void serialize(
      ColumnCheckConstraint columnCheckConstraint, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeStartObject();
    gen.writeObjectFieldStart("check_constraint");
    gen.writeObjectFieldStart("parameters");
    if (columnCheckConstraint.getMin() != null) {
      gen.writeStringField("min_value", columnCheckConstraint.getMin().toString());
    }
    if (columnCheckConstraint.getMax() != null) {
      gen.writeStringField("max_value", columnCheckConstraint.getMax().toString());
    }
    if (!columnCheckConstraint.getAcceptedValues().isEmpty()) {
      gen.writeArrayFieldStart("accepted_values");
      for (var acceptedValue : columnCheckConstraint.getAcceptedValues()) {
        gen.writeObject(acceptedValue);
      }
      gen.writeEndArray();
    }
    gen.writeEndObject();
    gen.writeEndObject();
    gen.writeEndObject();
  }
}
