package Data.Column;

import Data.DataType;
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
        Assertions.assertThrows(CheckConstraintException.class, () -> {
            column.addConstraint(new CheckConstraint(0, Integer.MAX_VALUE));
            column.isValid(-1);
        });
    }
}