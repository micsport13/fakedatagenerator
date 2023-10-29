package com.fdg.fakedatagenerator.serializers.table;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fdg.fakedatagenerator.table.Table;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class TableSerializer extends StdSerializer<Table> {
    // TODO: Standardize these field names for the yml file
    public TableSerializer() {
        this(null);
    }

    protected TableSerializer(Class<Table> t) {
        super(t);
    }

    @Override
    public void serialize(Table table, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        log.info("Serializing table: " + table.getName());
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
