package com.fakedatagenerator.serializers.schema;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.constraints.UniqueConstraint;
import com.fakedatagenerator.datatypes.IntegerDataType;
import com.fakedatagenerator.schema.Schema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
                    - name: id
                      type:
                        name: int
                  constraints:
                    - constraint:
                        type: unique
                      columns:
                        - id
                          """; // TODO: Put this in file instead of hardcoded test case
    Schema schema = objectMapper.readValue(expectedYaml, Schema.class);
    Column<IntegerDataType> column = new Column<>("id", new IntegerDataType());
    Schema expectedSchema = new Schema(column);
    expectedSchema.addConstraint(new UniqueConstraint(), column);
    assertEquals(expectedSchema, schema);
  }
}
