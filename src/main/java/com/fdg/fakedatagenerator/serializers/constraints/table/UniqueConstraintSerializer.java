package com.fdg.fakedatagenerator.serializers.constraints.table;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fdg.fakedatagenerator.constraints.table.UniqueConstraint;
import java.io.IOException;

public class UniqueConstraintSerializer extends StdSerializer<UniqueConstraint> {
  public UniqueConstraintSerializer() {
    this(null);
  }

  protected UniqueConstraintSerializer(Class<UniqueConstraint> t) {
    super(t);
  }

  @Override
  public void serialize(UniqueConstraint value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeString("unique");
  }
}
