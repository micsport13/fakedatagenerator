package com.fdg.fakedatagenerator.serializers.column;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fdg.fakedatagenerator.column.Column;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2

public class ColumnSerializer extends StdSerializer<Column<?>> {
    public ColumnSerializer() {
        this(null);
    }

    protected ColumnSerializer(Class<Column<?>> t) {
        super(t);
    }

    @Override
    public void serialize(Column column, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        log.info("Serializing column: " + column);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", column.getName());
        jsonGenerator.writeStringField("dataType", column.getDataType().toString());
        jsonGenerator.writeArrayFieldStart("constraints");
        for (var columnValidator : column.getConstraints()) {
            jsonGenerator.writeString(columnValidator.getClass().getName());
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }

}
