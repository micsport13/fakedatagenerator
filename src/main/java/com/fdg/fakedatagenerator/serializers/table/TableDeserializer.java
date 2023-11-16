package com.fdg.fakedatagenerator.serializers.table;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fdg.fakedatagenerator.table.Table;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TableDeserializer extends StdDeserializer<Table> {
  // TODO: Standardize these field names for the yml file
  protected TableDeserializer(Class<Table> t) {
    super(t);
  }

  @Override
  public Table deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException { // TODO: Work on updating this to match serializer
    log.info("Deserializing table");
    return null;
  }
}
