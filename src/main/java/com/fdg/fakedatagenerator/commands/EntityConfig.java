package com.fdg.fakedatagenerator.commands;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fdg.fakedatagenerator.table.Table;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import net.datafaker.Faker;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
public class EntityConfig {

  ObjectMapper objectMapper =
      new YAMLMapper()
          .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
          .enable(YAMLGenerator.Feature.INDENT_ARRAYS)
          .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR)
          .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
          .disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID)
          .enable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
          .enable(JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION);
  ;

  /*
  public ObjectMapper objectMapper() {
    return new YAMLMapper()
        .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
        .enable(YAMLGenerator.Feature.INDENT_ARRAYS)
        .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR)
        .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
        .disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID)
        .enable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        .enable(JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION);
  }
  */

  public List<Table> loadConfig(String filePath) throws IOException {
    InjectableValues.Std inject = new InjectableValues.Std();
    inject.addValue(Faker.class, new Faker());
    this.objectMapper.setInjectableValues(inject);
    return this.objectMapper.readValue(new File(filePath), new TypeReference<>() {});
  }

  public void writeConfig(String filePath, DataManager dataManager) throws IOException {
    this.objectMapper.writeValue(new File(filePath), dataManager.getTables().values());
  }
}
