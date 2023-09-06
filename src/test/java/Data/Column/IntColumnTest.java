package Data.Column;

import Data.DataType;
import Data.Exceptions.MismatchedDataTypeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The type Int column test.
 */
class IntColumnTest {
    private Column intColumn;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        intColumn = new Column("int", DataType.INT);
    }

    /**
     * Int object is valid.
     */
    @Test
    public void intObjectIsValid() {
        assertTrue(intColumn.isValid(1));
    }

    /**
     * Convertible string to int is valid.
     */
    @Test
    public void convertibleStringToIntIsValid() {
        Assertions.assertDoesNotThrow(() -> {
            intColumn.isValid("1");
        });
    }

    /**
     * Convertible double is valid.
     */
    @Test
    public void convertibleDoubleIsValid() {
        Assertions.assertDoesNotThrow(() -> {
            intColumn.isValid(1.0);
        });
    }

    /**
     * Inconvertible string throws exception.
     */
    @Test
    public void inconvertibleStringThrowsException() {
        Assertions.assertThrows(MismatchedDataTypeException.class, () -> {
            intColumn.isValid("Test");
        });
    }
}