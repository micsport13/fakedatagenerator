package com.fdg.fakedatagenerator.serializers.column;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.serializers.ColumnConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.jupiter.api.Assertions.*;

class ColumnSerializerTest {
  private ColumnSerializer columnSerializer;
  @BeforeEach
  public void setUp() throws IOException {
    this.columnSerializer = new ColumnSerializer();
  }

  @Test

  public void serialize_ColumnSerialization_OutputsCorrectSerialization() throws IOException {
    Column<IntegerDataType> intColumn = new Column<>("intColumn", new IntegerDataType());
    StringWriter ymlWriter = new StringWriter();
    YAMLGenerator ymlGenerator = new YAMLFactory().createGenerator(ymlWriter).disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
    columnSerializer.serialize(intColumn, ymlGenerator, null);
    ymlGenerator.flush();
    // Assert
    String expectedYaml = """
            ---
            name: "intColumn"
            dataType:
              typeName: "Integer"
            """;
    assertTrue(expectedYaml.trim().equals(ymlWriter.toString()));
  }
}
