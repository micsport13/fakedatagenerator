package com.fdg.fakedatagenerator.serializers.datatype;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;

import java.io.IOException;

public class IntegerDataTypeSerializer extends StdSerializer<IntegerDataType> {
  public IntegerDataTypeSerializer() {
    this(null);
  }

  protected IntegerDataTypeSerializer(Class<IntegerDataType> t) {
    super(t);
  }

  @Override
  public void serialize(IntegerDataType value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeStartObject();
    gen.writeStringField("name", "integer");
    gen.writeEndObject();
  }
}
