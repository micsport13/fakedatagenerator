package com.fdg.fakedatagenerator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class FakeDataGeneratorConfiguration {
  @Bean
  @Scope("singleton")
  public ObjectMapper objectMapper() {
    InjectableValues.Std inject = new InjectableValues.Std();
    inject.addValue("faker", faker());
    return new YAMLMapper()
        .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
        .enable(YAMLGenerator.Feature.INDENT_ARRAYS)
        .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR)
        .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
        .disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID)
        .enable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        .disable(JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION)
        .setInjectableValues(inject);
  }

  @Bean
  @Scope("singleton")
  public Faker faker() {
    return new Faker();
  }
}
