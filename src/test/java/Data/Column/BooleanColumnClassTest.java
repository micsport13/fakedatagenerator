package Data.Column;

import Data.DataType;
import Data.Exceptions.MismatchedDataTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BooleanColumnClassTest {
    private Column booleanColumn;
    @BeforeEach
    public void setup() {
        booleanColumn = new Column("boolean", DataType.BOOLEAN);
    }

    @Test
    public void validInputThrowsNoException() {
        assertTrue(booleanColumn.isValid(true));
    }
    @Test
    public void invalidInputThrowsMismatchedDataTypeException() {
        assertThrows(MismatchedDataTypeException.class, () -> booleanColumn.isValid(3));
    }
    @Test
    public void inputOfZeroIsValid() {
        assertTrue(booleanColumn.isValid(0));
    }
    @Test
    public void inputOfOneIsValid() {
        assertTrue(booleanColumn.isValid(1));
    }
    @Test
    public void inputOfTrueIsValid() {
        assertTrue(booleanColumn.isValid(true));
    }
    @Test
    public void inputOfFalseIsValid() {
        assertTrue(booleanColumn.isValid(false));
    }
    @Test void inputOfFalseStringThrowsMismatchedDataTypeException() {
        assertThrows(MismatchedDataTypeException.class, () -> booleanColumn.isValid("false"));
    }
    @Test void inputOfTrueStringThrowsMismatchedDataTypeException() {
        assertThrows(MismatchedDataTypeException.class, () -> booleanColumn.isValid("true"));
    }
    @Test void inputOfZeroStringThrowsMismatchedDataTypeException() {
        assertThrows(MismatchedDataTypeException.class, () -> booleanColumn.isValid("0"));
    }
}
