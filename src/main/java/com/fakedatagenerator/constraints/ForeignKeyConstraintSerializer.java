package com.fakedatagenerator.constraints;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

public class ForeignKeyConstraintSerializer extends JsonSerializer<ForeignKeyConstraint> {
  @Override
  public void serialize(
      ForeignKeyConstraint value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStringField("foreign_table", value.getForeignTable().getName());
    gen.writeStringField("foreign_column", value.getForeignColumnName());
  }

  @Override
  public void serializeWithType(
      ForeignKeyConstraint value,
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
