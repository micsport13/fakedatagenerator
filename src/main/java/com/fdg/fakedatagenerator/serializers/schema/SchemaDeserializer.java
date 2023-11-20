package com.fdg.fakedatagenerator.serializers.schema;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.table.TableConstraint;
import com.fdg.fakedatagenerator.schema.Schema;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SchemaDeserializer extends StdDeserializer<Schema> {
  // TODO: Standardize these field names for the yml file
  public SchemaDeserializer() {
    this(null);
  }
  protected SchemaDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Schema deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException { // TODO: Work on updating this to match serializer
    log.info("Deserializing schema");
    JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
    Map<Column<?>, Set<TableConstraint>> schemaMap = new HashMap<>();
    JsonNode schemaNode = rootNode.path("schema").get("columns");
    for (JsonNode node : schemaNode.findValues("column")) {
      Column<?> column = deserializationContext.readTreeAsValue(node, Column.class);
      Set<TableConstraint> tableConstraints = new HashSet<>();
      schemaMap.put(column, tableConstraints);
    }
    return new Schema(schemaMap);
  }
}
