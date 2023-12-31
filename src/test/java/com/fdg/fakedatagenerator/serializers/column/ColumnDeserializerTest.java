package com.fdg.fakedatagenerator.serializers.column;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.column.ColumnCheckConstraint;
import com.fdg.fakedatagenerator.constraints.column.ColumnConstraint;
import com.fdg.fakedatagenerator.constraints.column.NotNullConstraint;
import com.fdg.fakedatagenerator.datatypes.DecimalDataType;
import com.fdg.fakedatagenerator.datatypes.VarcharDataType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColumnDeserializerTest {

  private static final ObjectMapper objectMapper =
      new YAMLMapper()
          .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
          .enable(YAMLGenerator.Feature.INDENT_ARRAYS)
          .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR)
          .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES); ;

  @Test
  public void deserialize_GivenInputString_DeserializesToDecimalColumn() {
    String yamlString =
        """
                        name: decColumn
                        type:
                          name: decimal
                          parameters:
                            precision: 38
                            scale: 20
                        """;

    try {
      Column<?> column = objectMapper.readValue(yamlString, Column.class);
      Column<DecimalDataType> decColumn = new Column<>("decColumn", new DecimalDataType(38, 20));
      assertEquals(decColumn, column);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void deserialize_GivenInputString_DeserializesToVarcharColumn() {
    String yamlString =
        """
                        name: "varcharColumn"
                        type:
                          name: "varchar"
                          parameters:
                            max_length: 40
                        """;

    try {
      Column<?> column = objectMapper.readValue(yamlString, Column.class);
      Column<VarcharDataType> decColumn = new Column<>("varcharColumn", new VarcharDataType(40));
      assertEquals(decColumn, column);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void deserialize_WithConstraints_DeserializesWithoutErrors() {
    String yamlString = """
            name: testCol
            type:
              name: varchar
              parameters:
                max_length: 40
            constraints:
              - not_null""";
    try {
      Column<?> column = objectMapper.readValue(yamlString, Column.class);
      Column<VarcharDataType> decColumn = new Column<>("testCol", new VarcharDataType(40), new NotNullConstraint());
      assertEquals(decColumn, column);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void deserialize_WithCheckConstraint_DeserializesWithoutErrors() {
    String yamlString = """
            name: testCol
            type:
              name: decimal
              parameters:
                precision: 38
                scale: 20
            constraints:
              - check_constraint:
                  parameters:
                    min_value: 0
                    max_value: 10""";
    try {
      Column<?> column = objectMapper.readValue(yamlString, Column.class);
      ColumnConstraint checkConstraint = new ColumnCheckConstraint.Builder<>(new DecimalDataType(38, 20)).withRange(0, 10).build();
      Column<DecimalDataType> decColumn = new Column<>("testCol", new DecimalDataType(38,20), checkConstraint);
      assertEquals(decColumn, column);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
