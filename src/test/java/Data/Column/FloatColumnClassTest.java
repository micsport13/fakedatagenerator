package Data.Column;

import Data.DataType;
import Data.Exceptions.MismatchedDataTypeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * The type Float column class test.
 */
public class FloatColumnClassTest {
    private Column floatColumn;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        floatColumn = new Column("float", DataType.FLOAT);
    }

    /**
     * Valid float throws no exception.
     */
    @Test
    public void validFloatThrowsNoException() {
        Assertions.assertDoesNotThrow(() -> floatColumn.isValid(3.0));
    }

    /**
     * Integer throws no exception.
     */
    @Test
    public void integerThrowsNoException() {
        Assertions.assertDoesNotThrow(() -> floatColumn.isValid(3));
    }

    /**
     * String unable to convert throws exception.
     */
    @Test
    public void stringUnableToConvertThrowsException() {
        Assertions.assertThrows(MismatchedDataTypeException.class, () -> floatColumn.isValid("Test"));
    }

    /**
     * String able to convert throws no exception.
     */
    @Test
    public void stringAbleToConvertThrowsNoException() {
        Assertions.assertDoesNotThrow(() -> floatColumn.isValid("3.0"));
    }

    /**
     * Date time throws exception.
     */
    @Test
    public void dateTimeThrowsException() {
        Assertions.assertThrows(MismatchedDataTypeException.class, () -> floatColumn.isValid(ZonedDateTime.now()));
    }
}
