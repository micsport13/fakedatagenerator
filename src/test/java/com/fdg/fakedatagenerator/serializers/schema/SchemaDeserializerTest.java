package com.fdg.fakedatagenerator.serializers.schema;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.table.PrimaryKeyConstraint;
import com.fdg.fakedatagenerator.constraints.table.TableConstraint;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.schema.Schema;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SchemaDeserializerTest {

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
  public void deserialize_withValidYaml_ThrowsNoException() throws JsonProcessingException {
    String expectedYaml =
        """
                  columns:
                    - column:
                        name: id
                        type:
                          name: int
                      table_constraints:
                        - type: primary_key""";
    Schema schema = objectMapper.readValue(expectedYaml, Schema.class);
    Map<Column<?>, Set<TableConstraint>> schemaMap = new HashMap<>();
    schemaMap.put(
        new Column<IntegerDataType>("id", new IntegerDataType()),
        Set.of(new PrimaryKeyConstraint()));
    Schema expectedSchema = new Schema(schemaMap);
    assertEquals(expectedSchema, schema);
  }
}
