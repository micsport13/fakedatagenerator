package com.fdg.fakedatagenerator.column;

import static org.junit.jupiter.api.Assertions.*;

import com.fdg.fakedatagenerator.constraints.column.ColumnCheckConstraint;
import com.fdg.fakedatagenerator.constraints.column.ColumnConstraint;
import com.fdg.fakedatagenerator.constraints.column.NotNullConstraint;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.exceptions.CheckConstraintException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import com.fdg.fakedatagenerator.exceptions.NotNullConstraintException;
import com.fdg.fakedatagenerator.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** The type Column test. */
public class ColumnTest {
  /*
  TODO: Things to test
      Column state validation (Constraint is correct, Name is correct, dataType is correct)
      Column validator state (Constraint is correct, any column constraints are present)
      Prevent duplicate constraints (Also, prevent conflicting constraints)
      Name validation (Column cannot have special symbols)
   */
  /** The Int column. */
  public Column<?> intColumn;

  /** Sets . */
  @BeforeEach
  public void setup() {
    intColumn = new Column<>("int", new IntegerDataType());
  }

  // Constructors
  @Test
  public void constructor_WithValidNameAndDataType_SetsNameAndDataType() {
    assertEquals("int", intColumn.getName());
    assertEquals(IntegerDataType.class, intColumn.getDataType().getClass());
  }

  @Test
  public void constructor_WithValidNameDataTypeAndConstraints_SetsNameDataTypeAndConstraints() {
    ColumnConstraint columnConstraint = new NotNullConstraint();
    Column<?> column = new Column<>("int", new IntegerDataType(), columnConstraint);
    assertEquals("int", column.getName());
    assertEquals(IntegerDataType.class, column.getDataType().getClass());
    assertTrue(column.getConstraints().contains(columnConstraint));
  }

  // Add constraints
  @Test
  public void addConstraint_WithNullConstraint_AddsSuccessfullyToColumnConstraints() {
    ColumnConstraint columnConstraint = new NotNullConstraint();
    intColumn.addConstraint(columnConstraint);
    assertTrue(intColumn.getConstraints().contains(columnConstraint));
  }

  @Test
  public void addConstraint_WithValidCheckConstraint_AddsSuccessfullyToColumnConstraints() {
    ColumnConstraint columnConstraint =
        new ColumnCheckConstraint.Builder<>(this.intColumn.getDataType()).withRange(0, 10).build();
    intColumn.addConstraint(columnConstraint);
    assertTrue(intColumn.getConstraints().contains(columnConstraint));
  }

  @Test
  public void addMultipleConstraints_WithValidConstraints_IncludesAllConstraints() {
    this.intColumn.addConstraint(new NotNullConstraint());
    ColumnConstraint testCheckConstraint =
        new ColumnCheckConstraint.Builder<>(this.intColumn.getDataType())
            .withMinimumValue(1)
            .build();
    this.intColumn.addConstraint(testCheckConstraint);
    assertEquals(2, this.intColumn.getConstraints().size());
  }

  @Test
  public void addConstraint_WithConflictingConstraints_ThrowsIllegalArgumentException() {
    this.intColumn.addConstraint(new NotNullConstraint());
    ColumnConstraint testCheckConstraint =
        new ColumnCheckConstraint.Builder<>(this.intColumn.getDataType())
            .withMaximumValue(1)
            .build();
    this.intColumn.addConstraint(testCheckConstraint);
    ColumnConstraint testCheckConstraint2 =
        new ColumnCheckConstraint.Builder<>(this.intColumn.getDataType())
            .withMinimumValue(2)
            .build();
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          this.intColumn.addConstraint(testCheckConstraint2);
        });
  }

  // Equals
  @Test
  public void equals_WithAnotherColumnOfSameNameAndType_ColumnsAreEqual() {
    Column<IntegerDataType> column = new Column<>("int", new IntegerDataType());
    assertEquals(intColumn, column);
  }

  // IsValid
  @Test
  public void isValid_WithValidValue_ReturnsTrue() {
    assertDoesNotThrow(() -> intColumn.isValid(1));
  }

  @Test
  public void isValid_WithNullValueInNotNullColumn_ReturnsFalse() {
    Column<IntegerDataType> column =
        new Column<>("int", new IntegerDataType(), new NotNullConstraint());
    assertThrows(NotNullConstraintException.class, () -> column.isValid(null));
  }

  @Test
  public void isValid_WithNullValueInNullableColumn_ReturnsTrue() {
    Column<IntegerDataType> column = new Column<>("int", new IntegerDataType());
    assertDoesNotThrow(() -> column.isValid(null));
  }

  @Test
  public void isValid_WithViolatingCheckConstraintMax_ReturnsFalse() {
    ColumnCheckConstraint columnCheckConstraint =
        new ColumnCheckConstraint.Builder<>(new IntegerDataType()).withMaximumValue(10).build();
    Column<IntegerDataType> column =
        new Column<>("int", new IntegerDataType(), columnCheckConstraint);
    assertThrows(CheckConstraintException.class, () -> column.isValid(11));
  }

  @Test
  public void isValid_WithInvalidValue_ReturnsFalse() {
    assertThrows(MismatchedDataTypeException.class, () -> this.intColumn.isValid("test"));
  }
}
