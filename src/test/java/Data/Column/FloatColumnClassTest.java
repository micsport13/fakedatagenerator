package Data.Column;

import Data.DataType;
import Data.Exceptions.MismatchedDataTypeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class FloatColumnClassTest {
    private Column floatColumn;
    @BeforeEach
    public void setup() {
        floatColumn = new Column("float", DataType.FLOAT);
    }

    @Test
    public void validFloatThrowsNoException() {
        Assertions.assertDoesNotThrow(() -> floatColumn.isValid(3.0));
    }
    @Test
    public void integerThrowsNoException() {
        Assertions.assertDoesNotThrow(() -> floatColumn.isValid(3));
    }
    @Test
    public void stringUnableToConvertThrowsException() {
        Assertions.assertThrows(MismatchedDataTypeException.class, () -> floatColumn.isValid("Test"));
    }
    @Test
    public void stringAbleToConvertThrowsNoException() {
        Assertions.assertDoesNotThrow(() -> floatColumn.isValid("3.0"));
    }
    @Test
    public void dateTimeThrowsException() {
        Assertions.assertThrows(MismatchedDataTypeException.class, () -> floatColumn.isValid(ZonedDateTime.now()));
    }
}
