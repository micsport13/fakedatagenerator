package com.fdg.fakedatagenerator.serializers.constraints.table;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fdg.fakedatagenerator.constraints.table.PrimaryKeyConstraint;
import java.io.IOException;

public class PrimaryKeyConstraintSerializer extends StdSerializer<PrimaryKeyConstraint> {
  public PrimaryKeyConstraintSerializer() {
    this(null);
  }

  protected PrimaryKeyConstraintSerializer(Class<PrimaryKeyConstraint> t) {
    super(t);
  }

  @Override
  public void serialize(PrimaryKeyConstraint value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeString("primary_key");
  }
}
