package com.fdg.fakedatagenerator.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.UniqueConstraint;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.datatypes.VarcharDataType;
import com.fdg.fakedatagenerator.exceptions.UniqueConstraintException;
import com.fdg.fakedatagenerator.row.Row;
import com.fdg.fakedatagenerator.schema.Schema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** The type Table test. */
class TableTest {
  /*
  TODO: Testing validations
      Check state (Correct columns, correct multi constraints, name of multi)
      Table name validation
      Check adding row to make sure correct error is thrown.
      Check schema definition and schema order
   */
  private Table table;
  private Row testRow;

  /** Sets up. */
  @BeforeEach
  public void setUp() {
    Schema schema =
        new Schema(
            new Column<>("id", new IntegerDataType()),
            new Column<>("name", new VarcharDataType(10)));
    this.table = new Table("TableTest", schema);
    this.testRow =
        new Row.Builder(
                new Column<>("id", new IntegerDataType()),
                new Column<>("name", new VarcharDataType(10)))
            .withColumnValue("id", 1)
            .withColumnValue("name", "Dave")
            .build();
  }

  /** Add member throws no exception. */
  @Test
  public void add_WithValidEntity_SuccessfullyAddedToTable() {
    this.testRow.setColumnValue("id", 1);
    this.table.add(testRow);
  }

  /** Add multiple members throws no exception. */
  @Test
  public void add_WithMultipleEntities_AllAddedSucessfullyToTable() {
    this.table.add(this.testRow);
    this.table.add(this.testRow);
    assertEquals(2, this.table.getEntities().size());
  }

  /** Add member with unique single throws no exception. */
  @Test
  public void addTableConstraint_WithUniqueColumn_ThrowsNoException() {
    Column<?> column = this.testRow.getColumnByName("name");
    this.table.addConstraint(new UniqueConstraint(), column);
    this.table.add(this.testRow);
    assertEquals(1, this.table.getEntities().size());
  }

  /** Add multiple members with unique values throws no exception. */
  @Test
  public void add_MultipleEntitiesOnUniqueColumn_ThrowsNoException() {
    Row testRow2 =
        new Row.Builder(
                new Column<>("id", new IntegerDataType()),
                new Column<>("name", new VarcharDataType(10)))
            .withColumnValue("id", 2)
            .withColumnValue("name", "John")
            .build();
    Column<?> column = this.testRow.getColumnByName("name");
    this.table.addConstraint(new UniqueConstraint(), column);
    this.table.add(testRow);
    Assertions.assertDoesNotThrow(() -> this.table.add(testRow2));
  }

  /** Add multiple members with non unique values throws exception. */
  @Test
  public void add_MultipleMembersWithNonUniqueIntoUniqueColumn_ValuesThrowsException() {
    Column<?> column = this.testRow.getColumnByName("name");
    this.table.addConstraint(new UniqueConstraint(), column);
    this.table.add(this.testRow);
    Assertions.assertThrows(UniqueConstraintException.class, () -> this.table.add(testRow));
  }
}
