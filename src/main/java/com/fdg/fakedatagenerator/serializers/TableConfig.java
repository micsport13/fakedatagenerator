package com.fdg.fakedatagenerator.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fdg.fakedatagenerator.commands.DataManager;
import com.fdg.fakedatagenerator.schema.Schema;
import java.io.File;
import java.io.IOException;

import com.fdg.fakedatagenerator.serializers.table.TableSerializer;
import com.fdg.fakedatagenerator.table.Table;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TableConfig {
  public static Table loadConfig(String filePath) throws IOException {
    log.info("Loading configuration with path: " + filePath);
    ObjectMapper objectMapper = new YAMLMapper();
    return objectMapper.readValue(new File(filePath), Table.class);
  }

  public static void writeConfig(String filePath, Table table) throws IOException {
    log.info("Writing configuration with path: " + filePath);
    ObjectMapper objectMapper =
        YAMLMapper.builder().enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR).disable(SerializationFeature.FAIL_ON_EMPTY_BEANS).build();
    objectMapper.writeValue(new File(filePath), table);
  }
}
