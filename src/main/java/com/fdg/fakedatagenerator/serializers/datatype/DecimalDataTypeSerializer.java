package com.fdg.fakedatagenerator.serializers.datatype;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fdg.fakedatagenerator.datatypes.DecimalDataType;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DecimalDataTypeSerializer extends StdSerializer<DecimalDataType> {

  public DecimalDataTypeSerializer() {
    this(null);
  }

  protected DecimalDataTypeSerializer(Class<DecimalDataType> t) {
    super(t);
  }

  @Override
  public void serialize(
      DecimalDataType decimalDataType, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    log.info("Serializing decimal data type");
    gen.writeStartObject();
    gen.writeStringField("name", "decimal");
    gen.writeObjectFieldStart("parameters");
    gen.writeNumberField("precision", decimalDataType.getPrecision());
    gen.writeNumberField("scale", decimalDataType.getScale());
    gen.writeEndObject();
    gen.writeEndObject();
  }
}
