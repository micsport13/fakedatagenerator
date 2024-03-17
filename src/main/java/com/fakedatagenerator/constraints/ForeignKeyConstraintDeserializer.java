package com.fakedatagenerator.constraints;

import com.fakedatagenerator.commands.DataManager;
import com.fakedatagenerator.table.Table;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class ForeignKeyConstraintDeserializer extends JsonDeserializer<ForeignKeyConstraint> {
  @Override
  public ForeignKeyConstraint deserialize(JsonParser jsonParser, DeserializationContext ctxt)
      throws IOException {
    DataManager dataManager = (DataManager) ctxt.findInjectableValue("datamanager", null, null);
    JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
    String foreignTableName = rootNode.get("foreign_table").asText();
    String foreignColumnName = rootNode.get("foreign_column").asText();
    if (foreignTableName == null) {
      throw new IllegalArgumentException(
          "Field foreign_table is missing from the foreign constraint object.");
    }
    if (foreignColumnName == null) {
      throw new IllegalArgumentException(
          "Field foreign_column is missing from the foreign constraint object");
    }
    Table foreignTable = dataManager.getTable(foreignTableName);
    return new ForeignKeyConstraint(foreignTable, foreignColumnName);
  }
}
