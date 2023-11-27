package com.fdg.fakedatagenerator.serializers.datatype;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fdg.fakedatagenerator.datatypes.VarcharDataType;
import java.io.IOException;

public class VarcharDataTypeSerializer extends StdSerializer<VarcharDataType> {
  public VarcharDataTypeSerializer() {
    this(null);
  }

  protected VarcharDataTypeSerializer(Class<VarcharDataType> t) {
    super(t);
  }

  @Override
  public void serialize(
      VarcharDataType varcharDataType, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeStartObject();
    gen.writeStringField("name", "varchar");
    gen.writeObjectFieldStart("parameters");
    gen.writeNumberField("max_length", varcharDataType.getMaxLength());
    gen.writeEndObject();
    gen.writeEndObject();
  }
}
