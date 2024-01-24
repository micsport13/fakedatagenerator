package com.fdg.fakedatagenerator.column;

import static org.junit.jupiter.api.Assertions.*;

import com.fdg.fakedatagenerator.constraints.single.ColumnLevelConstraint;
import com.fdg.fakedatagenerator.constraints.single.NotNullLevelConstraint;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** The type Column test. */
public class ColumnTest {
  /*
  TODO: Things to test
      Column state validation (Constraint is correct, Name is correct, dataType is correct)
      Column validator state (Constraint is correct, any single constraints are present)
      Prevent duplicate constraints (Also, prevent conflicting constraints)
      Name validation (Column cannot have special symbols)
   */
  /** The Int single. */
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
    ColumnLevelConstraint columnConstraint = new NotNullLevelConstraint();
    Column<?> column = new Column<>("int", new IntegerDataType());
    assertEquals("int", column.getName());
    assertEquals(IntegerDataType.class, column.getDataType().getClass());
  }

  // Add constraints

  // Equals
  @Test
  public void equals_WithAnotherColumnOfSameNameAndType_ColumnsAreEqual() {
    Column<IntegerDataType> column = new Column<>("int", new IntegerDataType());
    assertEquals(column.getDataType().hashCode(), intColumn.getDataType().hashCode());
    assertEquals(intColumn, column);
  }

  // IsValid
  @Test
  public void isValid_WithValidValue_ReturnsTrue() {
    assertDoesNotThrow(() -> intColumn.validate(1));
  }

  @Test
  public void isValid_WithNullValueInNullableColumn_ReturnsTrue() {
    Column<IntegerDataType> column = new Column<>("int", new IntegerDataType());
    assertDoesNotThrow(() -> column.validate(null));
  }

  @Test
  public void isValid_WithInvalidValue_ReturnsFalse() {
    assertThrows(MismatchedDataTypeException.class, () -> this.intColumn.validate("test"));
  }
}
