package Data.Column;

import Data.DataType.DataType;
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
    /**
     * The Int column.
     */
    public Column intColumn;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        intColumn = new Column("int", DataType.INT);
    }


    /**
     * Add null constraint.
     */
// Testing adding constraints
    @Test
    public void addNullConstraint() {
        ColumnValidator columnConstraint = new NotNullValidator();
        intColumn.addConstraint(columnConstraint);
        assertTrue(intColumn.getConstraints()
                           .contains(columnConstraint));
    }

    /**
     * Add check constraint.
     */
    @Test
    public void addCheckConstraint() {
        ColumnValidator columnConstraint = new ColumnCheckValidator.CheckConstraintBuilder(this.intColumn.getDataType()).withRange(0, 10)
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
    public void columnsWithSameParametersAreEqual() {
        Column column = new Column("int", DataType.INT);
        assertEquals(intColumn, column);
    }

}
