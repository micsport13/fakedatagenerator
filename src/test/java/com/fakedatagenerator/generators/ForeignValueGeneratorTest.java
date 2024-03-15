package com.fakedatagenerator.generators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.commands.DataManager;
import com.fakedatagenerator.datatypes.VarcharDataType;
import com.fakedatagenerator.schema.Schema;
import com.fakedatagenerator.table.Table;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
public class ForeignValueGeneratorTest {

  @Autowired private ObjectMapper objectMapper;
  @Autowired private DataManager dataManager;
  @Autowired private Faker faker;

  @Test
  public void serialize_ForeignValueGenerator_serializesCorrectly() throws JsonProcessingException {
    Schema testSchema = new Schema(new Column("testColumn", new VarcharDataType(40)));
    Table testTable = new Table("test", testSchema);
    ForeignValueGenerator foreignValueGenerator =
        new ForeignValueGenerator(faker, testTable, "testColumn");
    String expectedYaml =
        """
    type: foreign_values
    table_name: test
    column_name: testColumn
    """;
    assertEquals(objectMapper.writeValueAsString(foreignValueGenerator), expectedYaml);
  }

  @Test
  public void deserialize_ForeignValueGenerator_returnsCorrectForeignValueGenerator()
      throws JsonProcessingException {
    String testYml =
        """
    type: foreign_values
    table_name: test
    column_name: testColumn
    """;
    Schema testSchema = new Schema(new Column("testColumn", new VarcharDataType(40)));
    Table testTable = new Table("test", testSchema);
    dataManager.addTable(testTable);
    assertDoesNotThrow(() -> objectMapper.readValue(testYml, ForeignValueGenerator.class));
  }
}
