package Data.Validators.ColumnValidators;

import Data.Column.Column;
import Data.DataType.DataType;
import Data.Exceptions.CheckConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The type Column check constraint test.
 */
class ColumnCheckValidatorTest {
    private Column intColumn;
    private Column varcharColumn;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        this.intColumn = new Column("int", DataType.INT);
        this.varcharColumn = new Column("string", DataType.VARCHAR);
    }

    /**
     * Value below minimum check constraint throws exception.
     */
// Testing Minimum Values
    @Test
    public void valueBelowMinimumCheckConstraintThrowsException() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder(this.intColumn.getDataType()).withMinimumValue(0.0)
                .build();
        Assertions.assertThrows(CheckConstraintException.class, () -> {
            intColumn.addConstraint(columnCheckConstraint);
            intColumn.isValid(-1);
        });
    }

    /**
     * Value at minimum check constraint throws no exception.
     */
    @Test
    public void valueAtMinimumCheckConstraintThrowsNoException() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder(this.intColumn.getDataType()).withMinimumValue(0)
                .build();
        assertTrue(columnCheckConstraint.validate(-.05));
    }


    /**
     * Value above maximum check constraint throws exception.
     */
// Testing Maximum Values
    @Test
    public void valueAboveMaximumCheckConstraintThrowsException() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder(this.intColumn.getDataType()).withMaximumValue(10.0)
                .build();
        Assertions.assertThrows(CheckConstraintException.class, () -> {
            intColumn.addConstraint(columnCheckConstraint);
            intColumn.isValid(11);
        });
    }

    /**
     * Value at maximum check constraint throws no exception.
     */
    @Test
    public void valueAtMaximumCheckConstraintThrowsNoException() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder(this.intColumn.getDataType()).withMaximumValue(10)
                .build();
        assertTrue(columnCheckConstraint.validate(10));
    }

    /**
     * Value between minimum and maximum check constraint throws no exception.
     */
// Testing Ranged Values
    @Test
    public void valueBetweenMinimumAndMaximumCheckConstraintThrowsNoException() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder(this.intColumn.getDataType()).withRange(0, 10)
                .build();
        assertTrue(columnCheckConstraint.validate(5));
    }

    /**
     * Value not in accepted values throws exception.
     */
// Testing Accepted Values
    @Test
    public void valueNotInAcceptedValuesThrowsException() {
        ColumnCheckValidator
                columnCheckConstraint =
                new ColumnCheckValidator.CheckConstraintBuilder(this.varcharColumn.getDataType()).withAcceptedValues("Test", "Test1")
                        .build();
        varcharColumn.addConstraint(columnCheckConstraint);
        Assertions.assertThrows(CheckConstraintException.class, () -> varcharColumn.isValid("Test3"));
    }

    /**
     * Value in accepted values throws no exception.
     */
    @Test
    public void valueInAcceptedValuesThrowsNoException() {
        ColumnCheckValidator
                columnCheckConstraint =
                new ColumnCheckValidator.CheckConstraintBuilder(this.varcharColumn.getDataType()).withAcceptedValues("Test", "Test1")
                        .build();
        varcharColumn.addConstraint(columnCheckConstraint);
        Assertions.assertDoesNotThrow(() -> varcharColumn.isValid("Test"));
    }

    /**
     * Multiple check constraints with same minimum are equal.
     */
// Comparing Constraints
    @Test
    public void multipleCheckConstraintsWithSameMinimumAreEqual() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder(this.intColumn.getDataType()).withMinimumValue(0)
                .withMaximumValue(10)
                .build();
        ColumnCheckValidator
                columnCheckConstraint1 = new ColumnCheckValidator.CheckConstraintBuilder(this.intColumn.getDataType()).withMinimumValue(0)
                .withMaximumValue(10)
                .build();
        Assertions.assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with same maximum are equal.
     */
    @Test
    public void multipleCheckConstraintsWithSameMaximumAreEqual() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder(this.intColumn.getDataType()).withMaximumValue(10)
                .build();
        ColumnCheckValidator
                columnCheckConstraint1 = new ColumnCheckValidator.CheckConstraintBuilder(this.intColumn.getDataType()).withMaximumValue(10)
                .build();
        Assertions.assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with same range are equal.
     */
    @Test
    public void multipleCheckConstraintsWithSameRangeAreEqual() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder(this.intColumn.getDataType()).withMinimumValue(0)
                .withMaximumValue(10)
                .build();
        ColumnCheckValidator
                columnCheckConstraint1 = new ColumnCheckValidator.CheckConstraintBuilder(this.intColumn.getDataType()).withMinimumValue(0)
                .withMaximumValue(10)
                .build();
        Assertions.assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with different ranges are not equal.
     */
    @Test
    public void multipleCheckConstraintsWithDifferentRangesAreNotEqual() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder(this.intColumn.getDataType()).withMinimumValue(0)
                .withMaximumValue(10)
                .build();
        ColumnCheckValidator
                columnCheckConstraint1 = new ColumnCheckValidator.CheckConstraintBuilder(this.intColumn.getDataType()).withMinimumValue(0)
                .withMaximumValue(8)
                .build();
        Assertions.assertNotEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with same accepted values are equal.
     */
    @Test
    public void multipleCheckConstraintsWithSameAcceptedValuesAreEqual() {
        ColumnCheckValidator
                columnCheckConstraint =
                new ColumnCheckValidator.CheckConstraintBuilder(this.varcharColumn.getDataType()).withAcceptedValues("Test", "Test1")
                        .build();
        ColumnCheckValidator
                columnCheckConstraint1 =
                new ColumnCheckValidator.CheckConstraintBuilder(this.varcharColumn.getDataType()).withAcceptedValues("Test", "Test1")
                        .build();
        Assertions.assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with different accepted values are not equal.
     */
    @Test
    public void multipleCheckConstraintsWithDifferentAcceptedValuesAreNotEqual() {
        ColumnCheckValidator
                columnCheckConstraint =
                new ColumnCheckValidator.CheckConstraintBuilder(this.varcharColumn.getDataType()).withAcceptedValues("Test", "Test1")
                        .build();
        ColumnCheckValidator
                columnCheckConstraint1 = new ColumnCheckValidator.CheckConstraintBuilder(this.varcharColumn.getDataType()).withAcceptedValues("Test")
                .build();
        Assertions.assertNotEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with different ordered accepted values are equal.
     */
    @Test
    public void multipleCheckConstraintsWithDifferentOrderedAcceptedValuesAreEqual() {
        ColumnCheckValidator
                columnCheckConstraint =
                new ColumnCheckValidator.CheckConstraintBuilder(this.varcharColumn.getDataType()).withAcceptedValues("Test", "Test1")
                        .build();
        ColumnCheckValidator
                columnCheckConstraint1 =
                new ColumnCheckValidator.CheckConstraintBuilder(this.varcharColumn.getDataType()).withAcceptedValues("Test1", "Test")
                        .build();
        Assertions.assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }


}