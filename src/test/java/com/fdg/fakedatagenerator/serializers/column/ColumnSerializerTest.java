package com.fdg.fakedatagenerator.serializers.column;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.column.NotNullConstraint;
import com.fdg.fakedatagenerator.datatypes.DecimalDataType;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.datatypes.VarcharDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColumnSerializerTest {

  private static final ObjectMapper objectMapper =
          new YAMLMapper()
            .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
            .enable(YAMLGenerator.Feature.INDENT_ARRAYS)
            .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR)
            .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES); ;

  @BeforeEach
  public void setUp() {

  }

  @Test
  public void serialize_ColumnSerialization_OutputsCorrectSerialization() throws IOException {
    Column<IntegerDataType> intColumn = new Column<>("intColumn", new IntegerDataType());
    String yaml = objectMapper.writeValueAsString(intColumn);
    // Assert
    String expectedYaml =
        """
            name: intColumn
            type:
              name: integer
            """;
    assertEquals(expectedYaml, yaml);
  }

  @Test
  public void serialize_DecimalDataTypeColumnWithNotNullConstraint_OutputsCorrectSerialization()
      throws IOException {
    Column<DecimalDataType> decColumn =
        new Column<>("decColumn", new DecimalDataType(38, 20), new NotNullConstraint());
    String yaml = objectMapper.writeValueAsString(decColumn);
    // Assert
    String expectedYaml =
        """
                name: decColumn
                type:
                  name: decimal
                  parameters:
                    precision: 38
                    scale: 20
                constraints:
                  - not_null
                """;
    assertEquals(expectedYaml, yaml);
  }

  @Test
  public void serialize_VarcharDataTypeWithNotNullConstraints_OutputsCorrectSerialization() throws IOException {
    Column<VarcharDataType> varcharDataTypeColumn =
        new Column<>("varcharColumn", new VarcharDataType(40), new NotNullConstraint());
    String yaml = objectMapper.writeValueAsString(varcharDataTypeColumn);
    // Assert
    String expectedYaml =
            """
                    name: varcharColumn
                    type:
                      name: varchar
                      parameters:
                        max_length: 40
                    constraints:
                      - not_null
                    """;
    assertEquals(expectedYaml, yaml);
  }

  @Test
  public void serialize_IntegerDataTypeWithNotNullConstraints_OutputsCorrectSerialization() throws IOException {
    Column<IntegerDataType> varcharDataTypeColumn =
            new Column<>("integerColumn", new IntegerDataType(), new NotNullConstraint());
    String yaml = objectMapper.writeValueAsString(varcharDataTypeColumn);
    // Assert
    String expectedYaml =
            """
                    name: integerColumn
                    type:
                      name: integer
                    constraints:
                      - not_null
                    """;
    assertEquals(expectedYaml, yaml);
  }
}
