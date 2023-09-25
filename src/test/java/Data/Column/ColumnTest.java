package Data.Column;

import Data.Validators.ColumnValidators.ColumnCheckValidator;
import Data.Validators.ColumnValidators.ColumnValidator;
import Data.Validators.ColumnValidators.NotNullValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The type Column test.
 */
public class ColumnTest {
    /*
    TODO: Things to test
        Column state validation (Validator is correct, Name is correct, dataType is correct)
        Column validator state (Validator is correct, any column constraints are present)
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
        intColumn = new Column<>("int", Integer.class);
    }


    /**
     * Add null constraint.
     */
// Testing adding constraints
    @Test
    public void addConstraint_WithNullConstraint_AddsSuccessfullyToColumnConstraints() {
        ColumnValidator columnConstraint = new NotNullValidator();
        intColumn.addConstraint(columnConstraint);
        assertTrue(intColumn.getConstraints()
                           .contains(columnConstraint));
    }

    /**
     * Add check constraint.
     */
    @Test
    public void addConstraint_WithValidCheckConstraint_AddsSuccessfullyToColumnConstraints() {
        ColumnValidator columnConstraint = new ColumnCheckValidator.CheckConstraintBuilder<>(this.intColumn.getDataType()).withRange(0, 10)
                .build();
        intColumn.addConstraint(columnConstraint);
        assertTrue(intColumn.getConstraints()
                           .contains(columnConstraint));
    }

    /**
     * Columns with same parameters are equal.
     */
// Testing equality of columns
    @Test
    public void equals_WithAnotherColumnOfSameNameAndType_ColumnsAreEqual() {
        Column<Integer> column = new Column<>("int", Integer.class);
        assertEquals(intColumn, column);
    }

    @Test
    public void addMultipleConstraints_WithValidConstraints_IncludesAllConstraints() {
        this.intColumn.addConstraint(new NotNullValidator());
        ColumnValidator testCheckConstraint = new ColumnCheckValidator
                .CheckConstraintBuilder<>(this.intColumn.getDataType())
                .withMinimumValue(1).build();
        this.intColumn.addConstraint(testCheckConstraint);
        assertEquals(2, this.intColumn.getConstraints()
                .size());
    }

}
