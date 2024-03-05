package com.fakedatagenerator.generators;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class ValueGeneratorSerializer extends StdSerializer<ValueGenerator> {
  public ValueGeneratorSerializer() {
    this(null);
  }

  protected ValueGeneratorSerializer(Class<ValueGenerator> vc) {
    super(vc);
  }

  @Override
  public void serialize(ValueGenerator value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {}
}
