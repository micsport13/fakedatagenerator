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
  public void serialize(ColumnCheckConstraint value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeStartObject();
    gen.writeObjectFieldStart("parameters");
    gen.writeEndObject();
    gen.writeEndObject();
  }
}
