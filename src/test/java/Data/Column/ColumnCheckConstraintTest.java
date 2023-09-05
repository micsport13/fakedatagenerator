package Data.Column;

import Data.DataType;
import Data.Exceptions.CheckConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColumnCheckConstraintTest {
    private Column intColumn;
    private Column varcharColumn;

    @BeforeEach
    public void setup() {
        this.intColumn = new Column("int", DataType.INT);
        this.varcharColumn = new Column("string", DataType.VARCHAR);
    }
    @Test
    public void valueBelowMinimumCheckConstraintThrowsException() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0.0).withMaximumValue(Integer.MAX_VALUE).build();
        Assertions.assertThrows(CheckConstraintException.class, () -> {
            intColumn.addConstraint(columnCheckConstraint);
            intColumn.isValid(-1);
        });
    }
    @Test
    public void valueAboveMaximumCheckConstraintThrowsException() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0.0).withMaximumValue(10).build();
        Assertions.assertThrows(CheckConstraintException.class, () -> {
            intColumn.addConstraint(columnCheckConstraint);
            intColumn.isValid(11);
        });
    }
    @Test
    public void valueAtMinimumCheckConstraintThrowsNoException() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(Integer.MAX_VALUE).build();
        assertTrue(columnCheckConstraint.isValid(-.05));
    }
    @Test
    public void valueAtMaximumCheckConstraintThrowsNoException() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        assertTrue(columnCheckConstraint.isValid(10));
    }
    @Test
    public void valueBetweenMinimumAndMaximumCheckConstraintThrowsNoException() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        assertTrue(columnCheckConstraint.isValid(5));
    }
    @Test
    public void multipleCheckConstraintsWithSameRangeAreEqual() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        ColumnCheckConstraint
                columnCheckConstraint1 = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    @Test
    public void multipleCheckConstraintsWithSameMinimumAreEqual() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        ColumnCheckConstraint
                columnCheckConstraint1 = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }
    @Test
    public void multipleCheckConstraintsWithSameMaximumAreEqual() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMaximumValue(10).build();
        ColumnCheckConstraint
                columnCheckConstraint1 = new ColumnCheckConstraint.CheckConstraintBuilder().withMaximumValue(10).build();
        assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }
    @Test
    public void multipleCheckConstraintsWithSameAcceptedValuesAreEqual() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test", "Test1").build();
        ColumnCheckConstraint
                columnCheckConstraint1 = new ColumnCheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test", "Test1").build();
        assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }
    @Test
    public void multipleCheckConstraintsWithDifferentAcceptedValuesAreNotEqual() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test", "Test1").build();
        ColumnCheckConstraint
                columnCheckConstraint1 = new ColumnCheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test").build();
        assertNotEquals(columnCheckConstraint, columnCheckConstraint1);
    }
    @Test
    public void multipleCheckConstraintsWithDifferentRangesAreNotEqual() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(10).build();
        ColumnCheckConstraint
                columnCheckConstraint1 = new ColumnCheckConstraint.CheckConstraintBuilder().withMinimumValue(0).withMaximumValue(8).build();
        assertNotEquals(columnCheckConstraint, columnCheckConstraint1);
    }
    @Test
    public void multipleCheckConstraintsWithDifferentOrderedAcceptedValuesAreEqual() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test", "Test1").build();
        ColumnCheckConstraint
                columnCheckConstraint1 = new ColumnCheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test1", "Test").build();
        assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }
    @Test
    public void valueNotInAcceptedValuesThrowsException() {
        ColumnCheckConstraint
                columnCheckConstraint = new ColumnCheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test", "Test1").build();
        varcharColumn.addConstraint(columnCheckConstraint);
        Assertions.assertThrows(CheckConstraintException.class, () -> varcharColumn.isValid("Test3"));
    }

}