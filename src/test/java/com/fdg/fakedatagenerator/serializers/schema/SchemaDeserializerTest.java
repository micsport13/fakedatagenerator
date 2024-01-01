package com.fdg.fakedatagenerator.serializers.schema;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.table.PrimaryKeyConstraint;
import com.fdg.fakedatagenerator.constraints.table.TableConstraint;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.schema.Schema;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
class SchemaDeserializerTest {

  @Autowired private ObjectMapper objectMapper;

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
