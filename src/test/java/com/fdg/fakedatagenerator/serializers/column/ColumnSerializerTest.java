package com.fdg.fakedatagenerator.serializers.column;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.column.NotNullConstraint;
import com.fdg.fakedatagenerator.datatypes.DecimalDataType;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.serializers.ColumnConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.jupiter.api.Assertions.*;

class ColumnSerializerTest {

  ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    this.objectMapper =
        new YAMLMapper()
            .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
            .enable(YAMLGenerator.Feature.INDENT_ARRAYS)
            .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR);
  }

  @Test
  public void serialize_ColumnSerialization_OutputsCorrectSerialization() throws IOException {
    Column<IntegerDataType> intColumn = new Column<>("intColumn", new IntegerDataType());
    String yaml = objectMapper.writeValueAsString(intColumn);
    // Assert
    String expectedYaml =
        """
            name: "intColumn"
            dataType:
              typeName: "Integer"
            """;
    assertEquals(expectedYaml, yaml);
  }

  @Test
  public void serialize_ColumnSerializationWithParameters_OutputsCorrectSerialization()
      throws IOException {
    Column<DecimalDataType> decColumn =
        new Column<>("decColumn", new DecimalDataType(38, 20), new NotNullConstraint());
    String yaml = objectMapper.writeValueAsString(decColumn);
    // Assert
    String expectedYaml =
        """
                name: "decColumn"
                dataType:
                  typeName: "Decimal"
                  parameters:
                    precision: 38
                    scale: 20
                    roundingMode: "HALF_UP"
                constraints:
                  - "NotNullConstraint"
                """;
    assertEquals(expectedYaml, yaml);
  }
}
