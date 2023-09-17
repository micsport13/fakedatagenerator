package Data.Validators.DataTypeValidators;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FloatValidatorTest {
    private final FloatValidator floatValidator = new FloatValidator();

    /**
     * Valid float throws no exception.
     */
    @Test
    public void validFloatThrowsNoException() {
        assertTrue(floatValidator.validate(3.0));
    }

    /**
     * Integer throws no exception.
     */
    @Test
    public void integerThrowsNoException() {
        assertTrue(floatValidator.validate(3));
    }

    /**
     * String unable to convert throws exception.
     */
    @Test
    public void stringUnableToConvertThrowsException() {
        assertFalse(floatValidator.validate("Test"));
    }

    /**
     * String able to convert throws no exception.
     */
    @Test
    public void stringAbleToConvertThrowsNoException() {
        assertTrue(floatValidator.validate("3.0"));
    }

    /**
     * Date time throws exception.
     */
    @Test
    public void dateTimeThrowsException() {
        assertFalse(floatValidator.validate(ZonedDateTime.now()));
    }

}