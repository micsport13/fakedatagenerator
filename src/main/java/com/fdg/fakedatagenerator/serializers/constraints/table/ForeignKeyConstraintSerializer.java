package com.fdg.fakedatagenerator.serializers.constraints.table;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fdg.fakedatagenerator.constraints.table.ForeignKeyConstraint;
import java.io.IOException;

public class ForeignKeyConstraintSerializer extends StdSerializer<ForeignKeyConstraint> {
  public ForeignKeyConstraintSerializer() {
    this(null);
  }

  protected ForeignKeyConstraintSerializer(Class<ForeignKeyConstraint> t) {
    super(t);
  }

  @Override
  public void serialize(ForeignKeyConstraint value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    throw new UnsupportedOperationException("Foreign Key serializtion not yet implemented");
  }
}
