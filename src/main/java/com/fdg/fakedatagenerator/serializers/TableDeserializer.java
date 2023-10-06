package com.fdg.fakedatagenerator.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fdg.fakedatagenerator.table.Table;

import java.io.IOException;

public class TableDeserializer extends StdDeserializer<Table> {
    protected TableDeserializer(Class<Table> t) {
        super(t);
    }
    @Override
    public Table deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return null;
    }
}
