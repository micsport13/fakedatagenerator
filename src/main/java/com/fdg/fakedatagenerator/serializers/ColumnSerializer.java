package com.fdg.fakedatagenerator.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fdg.fakedatagenerator.column.Column;

import java.io.IOException;

public class ColumnSerializer extends StdSerializer<Column> {
    public ColumnSerializer() {
        this(null);
    }
    protected ColumnSerializer(Class<Column> t) {
        super(t);
    }

    @Override
    public void serialize(Column column, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", column.getName());
        jsonGenerator.writeStringField("dataType", column.getDataType().getSimpleName());
        jsonGenerator.writeArrayFieldStart("constraints");
        for (var columnValidator: column.getConstraints()) {
            jsonGenerator.writeString(columnValidator.getClass().getName());
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }

}
