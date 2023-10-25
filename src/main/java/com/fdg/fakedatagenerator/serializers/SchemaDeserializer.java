package com.fdg.fakedatagenerator.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fdg.fakedatagenerator.schema.Schema;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class SchemaDeserializer extends StdDeserializer<Schema> {
    protected SchemaDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Schema deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        log.info("Deserializing schema");
        return null;
    }
}
