package com.fakedatagenerator.seeders;

import static org.junit.jupiter.api.Assertions.*;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.datatypes.IntegerDataType;
import com.fakedatagenerator.datatypes.VarcharDataType;
import com.fakedatagenerator.schema.Schema;
import com.fakedatagenerator.table.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CSVSeederTest {
  private Table testTable;

  @BeforeEach
  void setUp() {
    Column idColumn = new Column("id", new IntegerDataType());
    Column nameColumn = new Column("name", new VarcharDataType(255));
    Schema testSchema = new Schema(idColumn, nameColumn);
    testTable = new Table("testTable", testSchema);
  }

  @Test
  public void seed_withValidCSVInput_returnsConstructedTable() {
    CSVSeeder csvSeeder = new CSVSeeder();
    csvSeeder.seed(testTable, "src/test/resources/test.csv");
    assertEquals(1, testTable.getEntities().size());
  }
}
