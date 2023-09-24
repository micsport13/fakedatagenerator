package Data.Validators.DataTypeValidators;

import Data.Exceptions.MismatchedDataTypeException;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FloatValidatorTest {
    private final FloatValidator floatValidator = new FloatValidator();

    /**
     * Valid float throws no exception.
     */
    @Test
    public void validate_FloatValue_ThrowsNoException() {
        assertDoesNotThrow(() -> floatValidator.validate(3.0));
    }

    /**
     * Integer throws no exception.
     */
    @Test
    public void validate_IntegerValue_ThrowsNoException() {
        assertDoesNotThrow(()-> floatValidator.validate(3));
    }

    /**
     * String unable to convert throws exception.
     */
    @Test
    public void validate_StringUnableToConvertToFloat_ThrowsException() {
        assertThrows(MismatchedDataTypeException.class, () -> floatValidator.validate("Test"));
    }

    /**
     * String able to convert throws no exception.
     */
    @Test
    public void validate_StringAbleToConvertToFloat_ThrowsNoException() {
        assertDoesNotThrow(() -> floatValidator.validate("3.0"));
    }

    /**
     * Date time throws exception.
     */
    @Test
    public void validate_DateTimeObject_ThrowsException() {
        assertThrows(MismatchedDataTypeException.class, () -> floatValidator.validate(ZonedDateTime.now()));
    }

    @Test
    public void validate_NullInput_ThrowsNoException() {
        assertDoesNotThrow(() -> floatValidator.validate(null));
    }

}