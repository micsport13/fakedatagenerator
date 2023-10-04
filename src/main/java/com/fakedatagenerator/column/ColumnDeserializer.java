package com.fakedatagenerator.column;

import com.fakedatagenerator.validators.ColumnValidators.ColumnValidator;
import com.fakedatagenerator.validators.ColumnValidators.ColumnValidatorFactory;
import com.fakedatagenerator.validators.ConstraintType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

import java.io.IOException;
import java.util.HashSet;

public class ColumnDeserializer extends StdDeserializer<Column<?>> {
    public ColumnDeserializer() {
        this(null);
    }
    protected ColumnDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Column<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String columnName = node.get("columnName").asText();
        Class<?> dataType = null;
        try {
        dataType = Class.forName(node.get("dataType").asText());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ColumnValidator[] constraints = {ColumnValidatorFactory.createValidator(ConstraintType.valueOf(node.get("constraints").asText()))};

        return new Column<>(columnName, dataType, constraints);
    }
}
