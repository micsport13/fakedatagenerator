package com.fdg.fakedatagenerator.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fdg.fakedatagenerator.column.Column;
import java.io.File;
import java.io.IOException;

public class ColumnConfig {
  public static final ObjectMapper objectMapper =
      YAMLMapper.builder()
          .enable(YAMLGenerator.Feature.INDENT_ARRAYS)
          .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
          .build();

  public static Column<?> loadConfig(String filePath) throws IOException {
    return objectMapper.readValue(new File(filePath), Column.class);
  }

  public static void writeConfig(String filePath, Column<?> column) throws IOException {
    objectMapper.writeValue(new File(filePath), column);
  }
}
