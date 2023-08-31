package Data.Column;

import Data.DataType;
import Data.Exceptions.CheckConstraintException;
import Data.Exceptions.MismatchedDataTypeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColumnTest {
    private Column column;
    @BeforeEach
    public void setup() {
        column = new Column("id", DataType.INT);
    }
    @Test
    public void addNullConstraint() {
        ColumnConstraint columnConstraint = new NotNullConstraint();
        column.addConstraint(columnConstraint);
        assertTrue(column.getConstraints().contains(columnConstraint));
    }
    @Test
    public void intObjectIsValid() {
        assertTrue(column.isValid(1));
    }
    @Test
    public void stringObjectIsInvalid() {
        Assertions.assertThrows(MismatchedDataTypeException.class, () -> {
            column.isValid("1");
        });
    }
    @Test
    public void doubleObjectIsInvalid() {
        Assertions.assertThrows(MismatchedDataTypeException.class, () -> {
            column.isValid(1.0);
        });
    }
    @Test
    public void valueBelowMinimumCheckConstraintThrowsException() {
        CheckConstraint checkConstraint = new CheckConstraint.CheckConstraintBuilder().withMinimumValue(0.0).withMaximumValue(Integer.MAX_VALUE).build();
        Assertions.assertThrows(CheckConstraintException.class, () -> {
            column.addConstraint(checkConstraint);
            column.isValid(-1);
        });
    }
    @Test
    public void valueAboveMaximumCheckConstraintThrowsException() {
        CheckConstraint checkConstraint = new CheckConstraint.CheckConstraintBuilder().withMinimumValue(0.0).withMaximumValue(10).build();
        Assertions.assertThrows(CheckConstraintException.class, () -> {
            column.addConstraint(checkConstraint);
            column.isValid(11);
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
}