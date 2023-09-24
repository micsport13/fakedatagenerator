package Data.Validators.DataTypeValidators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BooleanValidatorTest {
    private final BooleanValidator booleanValidator = new BooleanValidator();

    /**
     * Valid input throws no exception.
     */
// Testing Booleans
    @Test
    public void validate_validBooleanInput_ThrowsNoException() {
        assertDoesNotThrow(() -> booleanValidator.validate(true));
    }


    /**
     * Input of zero is valid.
     */
// Testing Integers in place of booleans
    @Test
    public void validate_InputOfZero_ThrowsNoException() {
        assertDoesNotThrow(() -> booleanValidator.validate(0));
    }

    /**
     * Input of one is valid.
     */
    @Test
    public void validate_InputOfOne_ThrowsNoException() {
        assertDoesNotThrow(() -> booleanValidator.validate(1));
    }

    /**
     * Input of true is valid.
     */
    @Test
    public void validate_InputOfTrue_ThrowsNoException() {
        assertDoesNotThrow(() -> booleanValidator.validate(true));
    }

    /**
     * Input of false is valid.
     */
    @Test
    public void validate_InputOfFalse_ThrowsNoException() {
        assertDoesNotThrow(() -> booleanValidator.validate(false));
    }
    @Test
    public void validate_NullInput_ThrowsNoException() {
        assertDoesNotThrow(()-> booleanValidator.validate(null));
    }
}