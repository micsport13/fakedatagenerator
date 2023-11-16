package com.fdg.fakedatagenerator.serializers.column;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.ColumnLevelConstraints;
import com.fdg.fakedatagenerator.constraints.column.ColumnConstraint;
import com.fdg.fakedatagenerator.constraints.column.ColumnConstraintFactory;
import com.fdg.fakedatagenerator.datatypes.DataType;
import com.fdg.fakedatagenerator.datatypes.factories.DataTypeFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ColumnDeserializer extends StdDeserializer<Column<?>> {
  // TODO: Standardize these field names for the yml file
  public ColumnDeserializer() {
    this(null);
  }

  protected ColumnDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Column<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException { // TODO: Work on updating this to match serializer
    JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
    String columnName = rootNode.path("schema").path("columns").get("name").asText();
    DataType<?> dataType = null;
    try {
      log.info("Deserializing column: " + columnName);
      Iterator<Map.Entry<String, JsonNode>> paramNode =
          rootNode.get("parameters").fields(); // TODO: Check if null before trying to get fields
      if (paramNode != null) {
        Map<String, Object> parameters =
            Stream.of(paramNode)
                .collect(
                    HashMap::new,
                    (map, entry) ->
                        map.put(entry.next().getKey(), entry.next().getValue().asText()),
                    HashMap::putAll);
        dataType = DataTypeFactory.create(rootNode.get("dataType").asText(), parameters);
      } else {
        dataType = DataTypeFactory.create(rootNode.get("dataType").asText(), null);
      }
    } catch (Exception e) {
      log.error(e);
    }
    ColumnConstraint[] constraints =
        Stream.of(rootNode.get("constraints").fields())
            .map(
                entry -> {
                  ColumnLevelConstraints constraint =
                      ColumnLevelConstraints.valueOf(
                          entry.next().getValue().asText().toLowerCase());
                  return ColumnConstraintFactory.createConstraint(constraint);
                })
            .toArray(ColumnConstraint[]::new);
    return new Column<>(columnName, dataType, constraints);
  }
}
