package com.fdg.fakedatagenerator.serializers.column;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.column.ColumnCheckConstraint;
import com.fdg.fakedatagenerator.constraints.column.ColumnConstraint;
import com.fdg.fakedatagenerator.constraints.column.ColumnConstraintFactory;
import com.fdg.fakedatagenerator.datatypes.DataType;
import com.fdg.fakedatagenerator.datatypes.factories.DataTypeFactory;
import java.io.IOException;
import java.util.*;

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
    String columnName = rootNode.get("name").asText();
    DataType<?> dataType = null;
    try {
      log.info("Deserializing column: " + columnName);
      String typeName = rootNode.path("type").get("name").asText();
      if (rootNode.path("type").get("parameters") != null) {
        Iterator<Map.Entry<String, JsonNode>> paramNode =
            rootNode.path("type").get("parameters").fields();
        Map<String, String> parameters = new HashMap<>();
        while (paramNode.hasNext()) {
          var currentEntry = paramNode.next();
          parameters.put(currentEntry.getKey(), currentEntry.getValue().asText());
        }
        dataType = DataTypeFactory.create(typeName, parameters);
      } else {
        dataType = DataTypeFactory.create(typeName, null);
      }
    } catch (Exception e) {
      log.error(e);
    }
    if (rootNode.get("constraints") != null) {
      List<ColumnConstraint> constraints = new ArrayList<>();
      for (Iterator<JsonNode> it = rootNode.get("constraints").iterator(); it.hasNext(); ) {
        JsonNode node = it.next();
        constraints.add(
            node.get("check_constraint") != null
                ? deserializationContext.readTreeAsValue(node, ColumnCheckConstraint.class)
                : ColumnConstraintFactory.createConstraint(node.textValue()));
      }
      return new Column<>(columnName, dataType, constraints.toArray(new ColumnConstraint[0]));
    } else {
      return new Column<>(columnName, dataType);
    }
  }
}
