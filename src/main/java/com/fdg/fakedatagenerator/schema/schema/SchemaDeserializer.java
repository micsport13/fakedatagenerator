package com.fdg.fakedatagenerator.schema.schema;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.Constraint;
import com.fdg.fakedatagenerator.schema.Schema;
import java.io.IOException;
import java.util.*;
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
      throws IOException {
    log.info("Deserializing schema");
    JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
    Set<Column<?>> columns = new LinkedHashSet<>();
    var schemaNode = rootNode.withArray("columns").elements();
    if (!schemaNode.hasNext()) {
      throw new IllegalArgumentException("Schema must have at least one single column defined");
    }
    while (schemaNode.hasNext()) {
      JsonNode node = schemaNode.next();
      Column<?> column = deserializationContext.readTreeAsValue(node, Column.class);
      columns.add(column);
    }
    Schema schema = new Schema(columns.toArray(new Column<?>[0]));
    Map<Constraint, Set<Column<?>>> constraintMap = new HashMap<>();
    var constraintsNode = rootNode.withArray("constraints").elements();
    while (constraintsNode.hasNext()) {
      JsonNode node = constraintsNode.next();
      Constraint constraint =
          deserializationContext.readTreeAsValue(node.get("constraint"), Constraint.class);
      Set<Column<?>> constraintColumns = new HashSet<>();
      var columnNode = node.withArray("columns").elements();
      while (columnNode.hasNext()) {
        JsonNode columnElement = columnNode.next();
        constraintColumns.add(schema.getColumn(columnElement.asText()));
      }
      constraintMap.put(constraint, constraintColumns);
    }
    if (constraintMap.isEmpty()) {
      return schema;
    }
    constraintMap.forEach(
        (constraint, column) -> schema.addConstraint(constraint, column.toArray(new Column<?>[0])));
    return schema;
  }
}
