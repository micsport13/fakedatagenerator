package com.fakedatagenerator.column;

import com.fakedatagenerator.validators.ColumnValidators.NotNullValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class ColumnConfig {

    public static Column<?> loadConfig(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.registerModule(new SimpleModule().addDeserializer(Column.class, new ColumnDeserializer()));
        return objectMapper.readValue(new File(filePath), Column.class);
    }

    public static void writeConfig(String filePath, Column<?> column) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.registerModule(new SimpleModule().addSerializer(Column.class, new ColumnSerializer()));
        objectMapper.writeValue(new File(filePath), column);
    }
}
