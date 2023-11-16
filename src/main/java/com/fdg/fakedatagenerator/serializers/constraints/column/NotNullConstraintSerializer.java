package com.fdg.fakedatagenerator.serializers.constraints.column;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fdg.fakedatagenerator.constraints.column.NotNullConstraint;
import java.io.IOException;

public class NotNullConstraintSerializer extends StdSerializer<NotNullConstraint> {
  public NotNullConstraintSerializer() {
    this(null);
  }

  protected NotNullConstraintSerializer(Class<NotNullConstraint> t) {
    super(t);
  }

  @Override
  public void serialize(NotNullConstraint value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeString("not_null");
  }
}
