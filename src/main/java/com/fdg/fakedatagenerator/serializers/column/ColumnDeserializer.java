package com.fdg.fakedatagenerator.serializers.column;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.datatypes.DataType;
import com.fdg.fakedatagenerator.datatypes.factories.DataTypeFactory;
import com.fdg.fakedatagenerator.validators.ColumnValidators.ColumnValidator;
import com.fdg.fakedatagenerator.validators.ColumnValidators.ColumnValidatorFactory;
import com.fdg.fakedatagenerator.validators.ConstraintType;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Log4j2
public class ColumnDeserializer extends StdDeserializer<Column<?>> {
    public ColumnDeserializer() {
        this(null);
    }

    protected ColumnDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Column<?> deserialize(JsonParser jsonParser,
                                                   DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String columnName = node.get("columnName").asText();
        DataType<?> dataType = null;
        try {
            log.info("Deserializing column: " + columnName);
            Map<String, Object> parameters = Stream.of(node.get("parameters").fields())
                    .collect(HashMap::new, (map, entry) -> map.put(entry.next().getKey(),
                                    entry.next().getValue().asText()),
                            HashMap::putAll);
            dataType = DataTypeFactory.create(node.get("dataType").asText(), parameters);
        } catch (Exception e) {
            log.error(e);
        }
        ColumnValidator[] constraints =
                Stream.of(node.get("constraints").fields()).map(entry -> {
                    ConstraintType constraint = ConstraintType.valueOf(entry.next().getValue().asText().toLowerCase());
                    return ColumnValidatorFactory.createValidator(constraint);
                }).toArray(ColumnValidator[]::new);
        return new Column<>(columnName, dataType, constraints);
        // parameterization
        // fix
    }
}
