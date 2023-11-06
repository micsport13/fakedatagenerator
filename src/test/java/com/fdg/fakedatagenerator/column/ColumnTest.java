package com.fdg.fakedatagenerator.column;

import static org.junit.jupiter.api.Assertions.*;

import com.fdg.fakedatagenerator.constraints.column.ColumnCheckConstraint;
import com.fdg.fakedatagenerator.constraints.column.ColumnConstraint;
import com.fdg.fakedatagenerator.constraints.column.NotNullConstraint;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The type Column test.
 */
public class ColumnTest {
    /*
    TODO: Things to test
        Column state validation (Constraint is correct, Name is correct, dataType is correct)
        Column validator state (Constraint is correct, any column constraints are present)
        Prevent duplicate constraints (Also, prevent conflicting constraints)
        Name validation (Column cannot have special symbols)
     */
    /**
     * The Int column.
     */
    public Column<?> intColumn;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        intColumn = new Column<>("int", new IntegerDataType());
    }


    /**
     * Add null constraint.
     */
    // Testing adding constraints
    @Test
    public void addConstraint_WithNullConstraint_AddsSuccessfullyToColumnConstraints() {
        ColumnConstraint columnConstraint = new NotNullConstraint();
        intColumn.addConstraint(columnConstraint);
        assertTrue(intColumn.getConstraints().contains(columnConstraint));
    }

    /**
     * Add check constraint.
     */
    @Test
    public void addConstraint_WithValidCheckConstraint_AddsSuccessfullyToColumnConstraints() {
        ColumnConstraint columnConstraint = new ColumnCheckConstraint.CheckConstraintBuilder<>(this.intColumn.getDataType()).withRange(0, 10)
                                                                                                                            .build();
        intColumn.addConstraint(columnConstraint);
        assertTrue(intColumn.getConstraints().contains(columnConstraint));
    }

    /**
     * Columns with same parameters are equal.
     */
    // Testing equality of columns
    @Test
    public void equals_WithAnotherColumnOfSameNameAndType_ColumnsAreEqual() {
        Column<IntegerDataType> column = new Column<>("int", new IntegerDataType());
        assertEquals(intColumn, column);
    }

    @Test
    public void addMultipleConstraints_WithValidConstraints_IncludesAllConstraints() {
        this.intColumn.addConstraint(new NotNullConstraint());
        ColumnConstraint testCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder<>(this.intColumn.getDataType()).withMinimumValue(1)
                                                                                                                               .build();
        this.intColumn.addConstraint(testCheckConstraint);
        assertEquals(2, this.intColumn.getConstraints().size());
    }

    @Test
    public void addConstraint_WithConflictingConstraints_ThrowsIllegalArgumentException() {
        this.intColumn.addConstraint(new NotNullConstraint());
        ColumnConstraint testCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder<>(this.intColumn.getDataType()).withMaximumValue(1)
                                                                                                                               .build();
        this.intColumn.addConstraint(testCheckConstraint);
        ColumnConstraint testCheckConstraint2 = new ColumnCheckConstraint.CheckConstraintBuilder<>(this.intColumn.getDataType()).withMinimumValue(2)
                                                                                                                                .build();
        assertThrows(IllegalArgumentException.class, () -> {
            this.intColumn.addConstraint(testCheckConstraint2);
        });
    }

}
