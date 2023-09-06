package Data.Column;

import Data.DataType;
import Data.Exceptions.CheckConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Column check constraint test.
 */
class ColumnCheckConstraintTest {
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
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0.0).withMaximumValue(Integer.MAX_VALUE).build();
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
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(Integer.MAX_VALUE).build();
        assertTrue(columnCheckConstraint.isValid(-.05));
    }

    /**
     * Value above maximum check constraint throws exception.
     */
// Testing Maximum Values
    @Test
    public void valueAboveMaximumCheckConstraintThrowsException() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0.0).withMaximumValue(10).build();
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
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        assertTrue(columnCheckConstraint.isValid(10));
    }

    /**
     * Value between minimum and maximum check constraint throws no exception.
     */
// Testing Ranged Values
    @Test
    public void valueBetweenMinimumAndMaximumCheckConstraintThrowsNoException() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        assertTrue(columnCheckConstraint.isValid(5));
    }

    /**
     * Value not in accepted values throws exception.
     */
// Testing Accepted Values
    @Test
    public void valueNotInAcceptedValuesThrowsException() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test", "Test1").build();
        varcharColumn.addConstraint(columnCheckConstraint);
        Assertions.assertThrows(CheckConstraintException.class, () -> varcharColumn.isValid("Test3"));
    }

    /**
     * Value in accepted values throws no exception.
     */
    @Test public void valueInAcceptedValuesThrowsNoException() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test", "Test1").build();
        varcharColumn.addConstraint(columnCheckConstraint);
        Assertions.assertDoesNotThrow(() -> varcharColumn.isValid("Test"));
    }

    /**
     * Multiple check constraints with same minimum are equal.
     */
// Comparing Constraints
    @Test
    public void multipleCheckConstraintsWithSameMinimumAreEqual() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        ColumnCheckConstraint
                columnCheckConstraint1 = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with same maximum are equal.
     */
    @Test
    public void multipleCheckConstraintsWithSameMaximumAreEqual() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMaximumValue(10).build();
        ColumnCheckConstraint
                columnCheckConstraint1 = new ColumnCheckConstraint.CheckConstraintBuilder().withMaximumValue(10).build();
        assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with same range are equal.
     */
    @Test
    public void multipleCheckConstraintsWithSameRangeAreEqual() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        ColumnCheckConstraint
                columnCheckConstraint1 = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with different ranges are not equal.
     */
    @Test
    public void multipleCheckConstraintsWithDifferentRangesAreNotEqual() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        ColumnCheckConstraint
                columnCheckConstraint1 = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(8).build();
        assertNotEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with same accepted values are equal.
     */
    @Test
    public void multipleCheckConstraintsWithSameAcceptedValuesAreEqual() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test", "Test1").build();
        ColumnCheckConstraint
                columnCheckConstraint1 = new ColumnCheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test", "Test1").build();
        assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with different accepted values are not equal.
     */
    @Test
    public void multipleCheckConstraintsWithDifferentAcceptedValuesAreNotEqual() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test", "Test1").build();
        ColumnCheckConstraint
                columnCheckConstraint1 = new ColumnCheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test").build();
        assertNotEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with different ordered accepted values are equal.
     */
    @Test
    public void multipleCheckConstraintsWithDifferentOrderedAcceptedValuesAreEqual() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test", "Test1").build();
        ColumnCheckConstraint
                columnCheckConstraint1 = new ColumnCheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test1", "Test").build();
        assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }


}