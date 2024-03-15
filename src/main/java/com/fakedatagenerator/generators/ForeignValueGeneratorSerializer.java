package com.fakedatagenerator.generators;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ForeignValueGeneratorSerializer extends JsonSerializer<ForeignValueGenerator> {

  @Override
  public void serialize(ForeignValueGenerator value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeStringField("table_name", value.getTable().getName());
    gen.writeStringField("column_name", value.getColumnName());
  }

  @Override
  public void serializeWithType(
      ForeignValueGenerator value,
      JsonGenerator gen,
      SerializerProvider provider,
      TypeSerializer typeSerializer)
      throws IOException {
    WritableTypeId typeId = typeSerializer.typeId(value, JsonToken.START_OBJECT);
    typeSerializer.writeTypePrefix(gen, typeId);
    serialize(value, gen, provider);
    typeSerializer.writeTypeSuffix(gen, typeId);
  }
}
