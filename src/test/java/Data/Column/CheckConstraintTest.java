package Data.Column;

import Data.DataType;
import Data.Exceptions.CheckConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckConstraintTest {
    private Column intColumn;
    private Column varcharColumn;

    @BeforeEach
    public void setup() {
        this.intColumn = new Column("int", DataType.INT);
        this.varcharColumn = new Column("string", DataType.VARCHAR);
    }
    @Test
    public void valueBelowMinimumCheckConstraintThrowsException() {
        CheckConstraint checkConstraint = new CheckConstraint.CheckConstraintBuilder().withMinimumValue(0.0).withMaximumValue(Integer.MAX_VALUE).build();
        Assertions.assertThrows(CheckConstraintException.class, () -> {
            intColumn.addConstraint(checkConstraint);
            intColumn.isValid(-1);
        });
    }
    @Test
    public void valueAboveMaximumCheckConstraintThrowsException() {
        CheckConstraint checkConstraint = new CheckConstraint.CheckConstraintBuilder().withMinimumValue(0.0).withMaximumValue(10).build();
        Assertions.assertThrows(CheckConstraintException.class, () -> {
            intColumn.addConstraint(checkConstraint);
            intColumn.isValid(11);
        });
    }
    @Test
    public void valueAtMinimumCheckConstraintThrowsNoException() {
        CheckConstraint checkConstraint = new CheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(Integer.MAX_VALUE).build();
        assertTrue(checkConstraint.isValid(-.05));
    }
    @Test
    public void valueAtMaximumCheckConstraintThrowsNoException() {
        CheckConstraint checkConstraint = new CheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        assertTrue(checkConstraint.isValid(10));
    }
    @Test
    public void valueBetweenMinimumAndMaximumCheckConstraintThrowsNoException() {
        CheckConstraint checkConstraint = new CheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        assertTrue(checkConstraint.isValid(5));
    }
    @Test
    public void multipleCheckConstraintsWithSameRangeAreEqual() {
        CheckConstraint checkConstraint = new CheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        CheckConstraint checkConstraint1 = new CheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        assertEquals(checkConstraint, checkConstraint1);
    }
    @Test
    public void multipleCheckConstraintsWithSameMinimumAreEqual() {
        CheckConstraint checkConstraint = new CheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        CheckConstraint checkConstraint1 = new CheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        assertEquals(checkConstraint, checkConstraint1);
    }
    @Test
    public void multipleCheckConstraintsWithSameMaximumAreEqual() {
        CheckConstraint checkConstraint = new CheckConstraint.CheckConstraintBuilder().withMaximumValue(10).build();
        CheckConstraint checkConstraint1 = new CheckConstraint.CheckConstraintBuilder().withMaximumValue(10).build();
        assertEquals(checkConstraint, checkConstraint1);
    }
    @Test
    public void multipleCheckConstraintsWithSameAcceptedValuesAreEqual() {
        CheckConstraint checkConstraint = new CheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test", "Test1").build();
        CheckConstraint checkConstraint1 = new CheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test", "Test1").build();
        assertEquals(checkConstraint, checkConstraint1);
    }
    @Test
    public void multipleCheckConstraintsWithDifferentAcceptedValuesAreNotEqual() {
        CheckConstraint checkConstraint = new CheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test", "Test1").build();
        CheckConstraint checkConstraint1 = new CheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test").build();
        assertNotEquals(checkConstraint, checkConstraint1);
    }
    @Test
    public void multipleCheckConstraintsWithDifferentOrderedAcceptedValuesAreEqual() {
        CheckConstraint checkConstraint = new CheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test", "Test1").build();
        CheckConstraint checkConstraint1 = new CheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test1","Test").build();
        assertEquals(checkConstraint, checkConstraint1);
    }

}