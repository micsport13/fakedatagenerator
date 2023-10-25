package com.fdg.fakedatagenerator.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fdg.fakedatagenerator.schema.Schema;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class SchemaSerializer extends StdSerializer<Schema> {
    public SchemaSerializer() {
        this(null);
    }

    protected SchemaSerializer(Class<Schema> t) {
        super(t);
    }

    @Override
    public void serialize(Schema schema, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        log.info("Serializing schema: " + schema.toString());
        jsonGenerator.writeStartObject();
        jsonGenerator.writeArrayFieldStart("columns");
        for (var column : schema.getColumns()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("name", column.getName());
            jsonGenerator.writeStringField("dataType", column.getDataType().getSimpleName());
            if (!column.getConstraints().isEmpty()) {
                jsonGenerator.writeArrayFieldStart("constraints");
                for (var constraint : column.getConstraints()) {
                    jsonGenerator.writeString(constraint.getClass().getName());
                }
                jsonGenerator.writeEndArray();
            }
            if (!schema.getTableConstraints().get(column).isEmpty()) {
                jsonGenerator.writeArrayFieldStart("table_constraints");
                for (var tableValidator : schema.getTableConstraints().get(column)) {
                    jsonGenerator.writeString(tableValidator.getClass().getName());
                }
                jsonGenerator.writeEndArray();
            }
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();

    }
}
