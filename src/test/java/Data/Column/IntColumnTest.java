package Data.Column;

import Data.DataType;
import Data.Exceptions.CheckConstraintException;
import Data.Exceptions.MismatchedDataTypeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class IntColumnTest {
    private Column intColumn;
    @BeforeEach
    public void setup() {
        intColumn = new Column("int", DataType.INT);
    }

    @Test
    public void intObjectIsValid() {
        assertTrue(intColumn.isValid(1));
    }
    @Test
    public void stringObjectIsInvalid() {
        Assertions.assertThrows(MismatchedDataTypeException.class, () -> {
            intColumn.isValid("1");
        });
    }
    @Test
    public void doubleObjectIsInvalid() {
        Assertions.assertThrows(MismatchedDataTypeException.class, () -> {
            intColumn.isValid(1.0);
        });
    }

}