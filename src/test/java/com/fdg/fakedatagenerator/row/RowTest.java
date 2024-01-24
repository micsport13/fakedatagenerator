package com.fdg.fakedatagenerator.row;

import static org.junit.jupiter.api.Assertions.*;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import org.junit.jupiter.api.Test;

/** The type Row test. */
class RowTest {
  /* TODO: Things to test
     Selecting Row values
     Inserting Row values
     Updating Row values
     Deleting Row values
  */
  @Test
  public void entityConstructor_WithCorrectValues_ResultsInCorrectEntity() {
    Column<IntegerDataType> idColumn = new Column<>("id", new IntegerDataType());
    Row row = new Row.Builder(idColumn).withColumnValue("id", 1).build();
    int id = row.getColumnValue("id");
    assertEquals(1, id);
    assertSame(row.getColumnByName("id"), idColumn);
    assertEquals(1, row.getColumns().size());
    assertTrue(row.getColumnValueMapping().containsKey(idColumn));
  }

  @Test
  public void entityConstructor_WithNoProvidedColumnValues_ResultsInEntityWithNullInColumn() {
    Column<IntegerDataType> idColumn = new Column<>("id", new IntegerDataType());
    Row row = new Row.Builder(idColumn).build();
    assertNull(row.getColumnValue("id"));
    assertNull(row.getColumnValueMapping().get(idColumn));
  }

  @Test
  public void getColumnValue_WithMatchingColumn_ReturnsCorrectValue() {
    Column<IntegerDataType> idColumn = new Column<>("id", new IntegerDataType());
    Column<IntegerDataType> idColumn2 = new Column<>("id", new IntegerDataType());
    Row row = new Row.Builder(idColumn).withColumnValue("id", 1).build();
    int id = row.getColumnValue(idColumn2);
    assertEquals(1, id);
  }
}
