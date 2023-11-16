package com.fdg.fakedatagenerator.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fdg.fakedatagenerator.schema.Schema;
import java.io.File;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SchemaConfig {
  public static final ObjectMapper objectMapper =
      YAMLMapper.builder()
          .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR)
          .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
          .build();

  public static Schema loadConfig(String filePath) throws IOException {
    log.info("Loading configuration with path: " + filePath);
    return objectMapper.readValue(new File(filePath), Schema.class);
  }

  public static void writeConfig(String filePath, Schema schema) throws IOException {
    log.info("Writing configuration with path: " + filePath);
    objectMapper.writeValue(new File(filePath), schema);
  }
}
