package com.fakedatagenerator.constraints;

import static org.junit.jupiter.api.Assertions.*;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.commands.DataManager;
import com.fakedatagenerator.datatypes.IntegerDataType;
import com.fakedatagenerator.exceptions.ForeignKeyConstraintException;
import com.fakedatagenerator.row.Row;
import com.fakedatagenerator.schema.Schema;
import com.fakedatagenerator.table.Table;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

/** The type Foreign key constraint test. */
@JsonTest
class ForeignKeyConstraintTest {
  @Autowired private DataManager dataManager;
  @Autowired private ObjectMapper objectMapper;
  private Table table;

  @BeforeEach
  void setUp() {
    Schema schema = new Schema(new Column("test_column", new IntegerDataType()));
    Table foreignTable = new Table("test_table", schema);
    Row testRow =
        new Row.Builder(foreignTable.getColumns().toArray(new Column[0]))
            .withColumnValue("test_column", 1)
            .build();
    foreignTable.add(testRow);
    this.table = foreignTable;
    this.dataManager.addTable(foreignTable);
  }

  @Test
  public void serialize_withValidInput_returnsCorrectlySerializedForeignKeyConstraint()
      throws JsonProcessingException {
    String expectedYaml =
        """
      type: foreign_key
      foreign_table: test_table
      foreign_column: test_column
      """;
    ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(table, "test_column");
    String outputYaml = objectMapper.writeValueAsString(foreignKeyConstraint);
    assertEquals(expectedYaml, outputYaml);
  }

  @Test
  public void deserialize_withValidInput_returnsCorrectlyDeserializedForeignKeyConstraint()
      throws JsonProcessingException {
    String providedYaml =
        """
      type: foreign_key
      foreign_table: test_table
      foreign_column: test_column
      """;
    ForeignKeyConstraint foreignKeyConstraint =
        objectMapper.readValue(providedYaml, ForeignKeyConstraint.class);
    assertEquals(foreignKeyConstraint.getForeignTable().getName(), "test_table");
    assertEquals(foreignKeyConstraint.getForeignColumnName(), "test_column");
  }

  @Test
  public void validate_withCorrectForeignKeyValue_doesNotThrowException() {
    ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(table, "test_column");
    assertDoesNotThrow(() -> foreignKeyConstraint.validate(1));
  }

  @Test
  public void validate_withWrongForeignKeyValue_throwsException() {
    ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(table, "test_column");
    assertThrows(ForeignKeyConstraintException.class, () -> foreignKeyConstraint.validate(2));
  }

  @Test
  public void validate_withWrongForeignKeyValueType_throwsException() {
    ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(table, "test_column");
    assertThrows(ForeignKeyConstraintException.class, () -> foreignKeyConstraint.validate("1"));
  }
}
