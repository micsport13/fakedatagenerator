package com.fdg.fakedatagenerator.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fdg.fakedatagenerator.table.Table;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
public class EntityConfig {

  /*=
      new YAMLMapper()
          .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
          .enable(YAMLGenerator.Feature.INDENT_ARRAYS)
          .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR)
          .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
          .disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID)
          .enable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
          .enable(JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION);
  ;*/

  public List<Table> loadConfig(String filePath) throws IOException {
    return new YAMLMapper().readValue(new File(filePath), new TypeReference<>() {});
  }

  public void writeConfig(String filePath, DataManager dataManager) throws IOException {
    new YAMLMapper().writeValue(new File(filePath), dataManager.getTables());
  }
}
