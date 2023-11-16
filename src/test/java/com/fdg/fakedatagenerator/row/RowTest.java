package com.fdg.fakedatagenerator.row;

import static org.junit.jupiter.api.Assertions.*;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import java.util.NoSuchElementException;
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
    int id = (int) row.getValue("id").orElseThrow(NoSuchElementException::new);
    assertEquals(1, id);
    assertSame(row.getColumnByName("id").orElseThrow(NoSuchElementException::new), idColumn);
    assertEquals(1, row.getColumns().size());
    assertTrue(row.getColumnValueMapping().containsValue(1));
    assertTrue(row.getColumnValueMapping().containsKey(idColumn));
  }

  @Test
  public void entityConstructor_WithNoProvidedColumnValues_ResultsInEntityWithNullInColumn() {
    Column<IntegerDataType> idColumn = new Column<>("id", new IntegerDataType());
    Row row = new Row.Builder(idColumn).build();
    assertTrue(row.getValue("id").isEmpty());
    assertNull(row.getColumnValueMapping().get(idColumn));
  }
}
