package com.fdg.fakedatagenerator.serializers.schema;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.table.TableConstraint;
import com.fdg.fakedatagenerator.constraints.table.TableConstraintFactory;
import com.fdg.fakedatagenerator.constraints.table.TableLevelConstraints;
import com.fdg.fakedatagenerator.schema.Schema;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

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
    Map<Column<?>, Set<TableConstraint>> schemaMap = new LinkedHashMap<>();
    var schemaNode = rootNode.withArray("columns").elements();
    if (!schemaNode.hasNext()) {
      throw new IllegalArgumentException("Schema must have at least one column defined");
    }
    while (schemaNode.hasNext()) {
      JsonNode node = schemaNode.next();
      Column<?> column = deserializationContext.readTreeAsValue(node.get("column"), Column.class);
      Set<TableConstraint> tableConstraints = new HashSet<>();
      var constraintNode = node.path("table_constraints").iterator();
      while (constraintNode.hasNext()) {
        tableConstraints.add(TableConstraintFactory.createConstraint(TableLevelConstraints.valueOf(constraintNode.next()
                                                                                                           .asText().toUpperCase())));
      }
      schemaMap.put(column, tableConstraints);
    }
    return new Schema(schemaMap);
  }
}
