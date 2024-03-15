package com.fakedatagenerator.generators;

import com.fakedatagenerator.commands.DataManager;
import com.fakedatagenerator.table.Table;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import net.datafaker.Faker;

public class ForeignValueGeneratorDeserializer extends JsonDeserializer<ForeignValueGenerator> {

  @Override
  public ForeignValueGenerator deserialize(JsonParser jsonParser, DeserializationContext ctxt)
      throws IOException, JacksonException {
    DataManager dataManager = (DataManager) ctxt.findInjectableValue("datamanager" ,null, null);
    JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
    Table table = dataManager.getTable(rootNode.get("table_name").asText());
    String columnName = rootNode.get("column_name").asText();
    return new ForeignValueGenerator(
        (Faker) ctxt.findInjectableValue("faker", null, null), table, columnName);
  }
}
