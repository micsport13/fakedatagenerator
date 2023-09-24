package Data.Validators.DataTypeValidators;

import Data.Exceptions.MismatchedDataTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntValidatorTest {
    private final IntValidator intValidator = new IntValidator();

    @Test
    public void validate_IntValue_ThrowsNoException() {
        assertDoesNotThrow(() -> intValidator.validate(1));
    }

    /**
     * Convertible string to int is valid.
     */
    @Test
    public void validate_ConvertibleStringValue_ThrowsMismatchedDataTypeException() {
        assertThrows(MismatchedDataTypeException.class, () -> intValidator.validate("1"));
    }

    /**
     * Convertible double is valid.
     */
    @Test
    public void validate_convertibleDoubleValue_ThrowsNoException() {
        assertDoesNotThrow(() -> intValidator.validate(1.0));
    }

    /**
     * Inconvertible string throws exception.
     */
    @Test
    public void validate_InconvertibleStringValue_ThrowsMismatchedDataTypeException() {
        assertThrows(MismatchedDataTypeException.class, () ->
                intValidator.validate("Test"));
    }

    @Test
    public void validate_NonNumericValue_ThrowsMismatchedDataTypeException() {
        assertThrows(MismatchedDataTypeException.class, () ->
                intValidator.validate(new Object()));
    }
    @Test
    public void validate_NullInput_ThrowsNoException() {
        assertDoesNotThrow(() -> intValidator.validate(null));
    }

}