package com.fakedatagenerator.column;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

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
        jsonGenerator.writeStringField("columnName", column.getName());
        jsonGenerator.writeStringField("dataType", column.getDataType().getSimpleName());
        jsonGenerator.writeArrayFieldStart("constraints");

        for (var constraint : column.getConstraints()) {
            jsonGenerator.writeString(constraint.getClass().getSimpleName());
        }

        // End the array field for constraints
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }

}
