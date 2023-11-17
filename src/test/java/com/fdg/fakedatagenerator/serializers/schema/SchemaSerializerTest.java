package com.fdg.fakedatagenerator.serializers.schema;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.table.PrimaryKeyConstraint;
import com.fdg.fakedatagenerator.constraints.table.UniqueConstraint;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.datatypes.VarcharDataType;
import com.fdg.fakedatagenerator.schema.Schema;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SchemaSerializerTest {

  ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    this.objectMapper =
        new YAMLMapper()
            .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
            .enable(YAMLGenerator.Feature.INDENT_ARRAYS)
            .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR)
            .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES);
  }

  @Test
  public void serialize_Schema_OutputsCorrectSerialization() throws IOException {
    Column<IntegerDataType> intColumn = new Column<>("intColumn", new IntegerDataType());
    Column<VarcharDataType> varcharColumn =
        new Column<VarcharDataType>("varcharColumn", new VarcharDataType());
    Schema schema = new Schema(intColumn, varcharColumn);
    schema.addConstraint(intColumn, new PrimaryKeyConstraint());
    schema.addConstraint(varcharColumn, new UniqueConstraint());
    String yaml = objectMapper.writeValueAsString(schema);
    // Assert
    String expectedYaml =
        """
                    columns:
                      - column:
                          name: intColumn
                          type:
                            name: integer
                        table_constraints:
                          - primary_key
                      - column:
                          name: varcharColumn
                          type:
                            name: varchar
                            parameters:
                              max_length: 1
                        table_constraints:
                          - unique
                    """;
    assertEquals(expectedYaml, yaml);
  }
}
