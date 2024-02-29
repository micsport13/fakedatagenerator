package com.fdg.fakedatagenerator.serializers.table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdg.fakedatagenerator.table.Table;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
class TableDeserializerTest {
  @Autowired private ObjectMapper objectMapper;

  @Test
  public void deserialize_withValidInput_returnsCorrectTableObject()
      throws JsonProcessingException {
    String testYaml =
        """
name: test
schema:
  columns:
    - name: id
      type:
        name: integer
    - name: first_name
      type:
        name: varchar
        max_length: 50
    - name: last_name
      type:
        name: varchar
        max_length: 50
  constraints:
    - constraint:
        type: primary_key
      columns:
        - id
    - constraint:
        type: unique
      columns:
        - first_name
        - last_name
        """;
    Table table = objectMapper.readValue(testYaml, Table.class);
    Assertions.assertEquals("test", table.getName());
    Assertions.assertEquals(3, table.getSchema().getColumns().size());
  }
}
