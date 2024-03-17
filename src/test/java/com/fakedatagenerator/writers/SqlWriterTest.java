package com.fakedatagenerator.writers;

import static org.junit.jupiter.api.Assertions.*;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.datatypes.IntegerDataType;
import com.fakedatagenerator.datatypes.VarcharDataType;
import com.fakedatagenerator.row.Row;
import com.fakedatagenerator.schema.Schema;
import com.fakedatagenerator.table.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SqlWriterTest {
  private Table testTable;

  @BeforeEach
  void setUp() {
    Column idColumn = new Column("id", new IntegerDataType());
    Column stringColumn = new Column("string", new VarcharDataType(255));
    Schema testSchema = new Schema(idColumn, stringColumn);
    this.testTable = new Table("test_table", testSchema);
  }

  @AfterEach
  void tearDown() {
    testTable.truncate();
  }

  @Test
  public void write_withValidValues_returnsValidSQLString() {
    Row testRow =
        new Row.Builder(testTable.getColumns().toArray(new Column[0]))
            .withColumnValue("id", 1)
            .withColumnValue("string", "SQL String with apostrophe '")
            .build();
    testTable.add(testRow);
    SqlWriter sqlWriter = new SqlWriter(testTable);
    String expectedSqlString =
        "INSERT INTO test_table (id, string) VALUES (1, 'SQL String with apostrophe ''');\n";
    assertEquals(expectedSqlString, sqlWriter.write());
  }
}
