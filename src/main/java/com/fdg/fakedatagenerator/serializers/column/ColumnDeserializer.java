package com.fdg.fakedatagenerator.serializers.column;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fdg.fakedatagenerator.column.Column;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class ColumnDeserializer extends StdDeserializer<Column<?>> {
    public ColumnDeserializer() {
        this(null);
    }

    protected ColumnDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Column<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String columnName = node.get("columnName").asText();
        Class<?> dataType = null;
        try {
            log.info("Deserializing column: " + columnName);
            dataType = Class.forName(node.get("dataType").asText());
        } catch (ClassNotFoundException e) {
            log.error(e);
        }
        //ColumnValidator[] constraints = {ColumnValidatorFactory.createValidator(ConstraintType.valueOf(node.get("constraints").asText()))};

        return new Column<>(columnName, dataType); // TODO: Deserialize constraints
    }
}
