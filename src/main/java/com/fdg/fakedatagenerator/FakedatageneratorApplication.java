package com.fdg.fakedatagenerator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import net.datafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.shell.command.annotation.CommandScan;

@SpringBootApplication
@CommandScan(basePackages = "com.fdg.fakedatagenerator.commands")
public class FakedatageneratorApplication {
  public static void main(String[] args) {
    SpringApplication.run(FakedatageneratorApplication.class, args);
  }

  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    return new YAMLMapper()
        .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
        .enable(YAMLGenerator.Feature.INDENT_ARRAYS)
        .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR)
        .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
        .disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID)
        .enable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        .disable(JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION);
  }

  @Bean
  public Faker faker() {
    return new Faker();
  }
}
