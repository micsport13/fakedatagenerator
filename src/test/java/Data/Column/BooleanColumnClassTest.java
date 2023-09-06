package Data.Column;

import Data.DataType;
import Data.Exceptions.MismatchedDataTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The type Boolean column class test.
 */
public class BooleanColumnClassTest {
    private Column booleanColumn;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        booleanColumn = new Column("boolean", DataType.BOOLEAN);
    }

    /**
     * Valid input throws no exception.
     */
// Testing Booleans
    @Test
    public void validInputThrowsNoException() {
        assertTrue(booleanColumn.isValid(true));
    }

    /**
     * Invalid input throws mismatched data type exception.
     */
    @Test
    public void invalidInputThrowsMismatchedDataTypeException() {
        assertThrows(MismatchedDataTypeException.class, () -> booleanColumn.isValid(3));
    }

    /**
     * Input of zero is valid.
     */
// Testing Integers in place of booleans
    @Test
    public void inputOfZeroIsValid() {
        assertTrue(booleanColumn.isValid(0));
    }

    /**
     * Input of one is valid.
     */
    @Test
    public void inputOfOneIsValid() {
        assertTrue(booleanColumn.isValid(1));
    }

    /**
     * Input of true is valid.
     */
    @Test
    public void inputOfTrueIsValid() {
        assertTrue(booleanColumn.isValid(true));
    }

    /**
     * Input of false is valid.
     */
    @Test
    public void inputOfFalseIsValid() {
        assertTrue(booleanColumn.isValid(false));
    }

    /**
     * Input of false string throws mismatched data type exception.
     */
// Asserting String is not a valid input
    @Test void inputOfFalseStringThrowsMismatchedDataTypeException() {
        assertThrows(MismatchedDataTypeException.class, () -> booleanColumn.isValid("false"));
    }

    /**
     * Input of true string throws mismatched data type exception.
     */
    @Test void inputOfTrueStringThrowsMismatchedDataTypeException() {
        assertThrows(MismatchedDataTypeException.class, () -> booleanColumn.isValid("true"));
    }

    /**
     * Input of zero string throws mismatched data type exception.
     */
    @Test void inputOfZeroStringThrowsMismatchedDataTypeException() {
        assertThrows(MismatchedDataTypeException.class, () -> booleanColumn.isValid("0"));
    }
}
