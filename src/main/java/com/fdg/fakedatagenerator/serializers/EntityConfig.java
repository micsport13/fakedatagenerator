package com.fdg.fakedatagenerator.serializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fdg.fakedatagenerator.commands.DataManager;
import com.fdg.fakedatagenerator.table.Table;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EntityConfig {
  public static final ObjectMapper objectMapper =
      new YAMLMapper()
          .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
          .disable(YAMLGenerator.Feature.INDENT_ARRAYS)
          .disable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR)
          .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
          .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

  public static List<Table> loadConfig(String filePath) throws IOException {
    return objectMapper.readValue(new File(filePath), new TypeReference<>() {});
  }

  public static void writeConfig(String filePath, DataManager dataManager) throws IOException {
    objectMapper.writeValue(new File(filePath), dataManager.getTables());
  }
}
