package com.fdg.fakedatagenerator.serializers.table;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fdg.fakedatagenerator.table.Table;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class TableDeserializer extends StdDeserializer<Table> {
    protected TableDeserializer(Class<Table> t) {
        super(t);
    }

    @Override
    public Table deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        log.info("Deserializing table");
        return null;
    }
}
