package com.fdg.fakedatagenerator.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fdg.fakedatagenerator.table.Table;

import java.io.IOException;

public class TableSerializer extends StdSerializer<Table> {
    public TableSerializer() {
        this(null);
    }
    protected TableSerializer(Class<Table> t) {
        super(t);
    }

    @Override
    public void serialize(Table table, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
