package com.fdg.fakedatagenerator.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fdg.fakedatagenerator.schema.Schema;

import java.io.IOException;

public class SchemaSerializer extends StdSerializer<Schema> {
    public SchemaSerializer() {
        this(null);
    }
    protected SchemaSerializer(Class<Schema> t) {
        super(t);
    }

    @Override
    public void serialize(Schema schema, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeArrayFieldStart("columns");
        for (var column: schema.getColumns()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("name", column.getName());
            jsonGenerator.writeStringField("dataType", column.getDataType().getSimpleName());

            jsonGenerator.writeArrayFieldStart("constraints");
            for (var tableValidator : schema.getTableConstraints().get(column)) {
                jsonGenerator.writeString(tableValidator.getClass().getName());
            }
            jsonGenerator.writeEndArray();
            jsonGenerator.writeArrayFieldStart("table_constraints");
            for (var constraint: column.getConstraints()) {
                jsonGenerator.writeString(constraint.getClass().getName());
            }
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
