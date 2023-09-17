package Data.Validators.DataTypeValidators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntValidatorTest {
    private final IntValidator intValidator = new IntValidator();

    @Test
    public void intObjectIsValid() {
        assertTrue(intValidator.validate(1));
    }

    /**
     * Convertible string to int is valid.
     */
    @Test
    public void convertibleStringToIntIsValid() {
        assertTrue(intValidator.validate("1"));
    }

    /**
     * Convertible double is valid.
     */
    @Test
    public void convertibleDoubleIsValid() {
        assertTrue(intValidator.validate(1.0));
    }

    /**
     * Inconvertible string throws exception.
     */
    @Test
    public void inconvertibleStringThrowsException() {
        assertFalse(
                intValidator.validate("Test"));
    }

    @Test
    public void otherObjectThrowsException() {
        assertFalse(
                intValidator.validate(new Object()));
    }

}