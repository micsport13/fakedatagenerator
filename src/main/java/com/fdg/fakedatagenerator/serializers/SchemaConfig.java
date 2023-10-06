package com.fdg.fakedatagenerator.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fdg.fakedatagenerator.schema.Schema;

import java.io.File;
import java.io.IOException;

public class SchemaConfig {
    public static Schema loadConfig(String filePath) throws IOException {
        ObjectMapper objectMapper = new YAMLMapper();
        return objectMapper.readValue(new File(filePath), Schema.class);
    }

    public static void writeConfig(String filePath, Schema schema) throws IOException {
        ObjectMapper objectMapper = YAMLMapper.builder().enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR).build();
        objectMapper.writeValue(new File(filePath), schema);
    }
}
