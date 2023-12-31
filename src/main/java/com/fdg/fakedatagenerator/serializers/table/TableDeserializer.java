package com.fdg.fakedatagenerator.serializers.table;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fdg.fakedatagenerator.schema.Schema;
import com.fdg.fakedatagenerator.table.Table;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class TableDeserializer extends StdDeserializer<Table> {
  // TODO: Standardize these field names for the yml file
  public TableDeserializer(){
    this(null);
  }
  protected TableDeserializer(Class<Table> t) {
    super(t);
  }

  @Override
  public Table deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException { // TODO: Work on updating this to match serializer
    log.info("Deserializing table");
    JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
    String tableName = rootNode.get("name").asText();
    Schema schema = deserializationContext.readTreeAsValue(rootNode.get("schema"), Schema.class);
    return new Table(tableName, schema);
  }
}
