package Data.Validators.DataTypeValidators;

import Data.Exceptions.MismatchedDataTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertThrows(MismatchedDataTypeException.class, () -> intValidator.validate("1"));
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
        assertThrows(MismatchedDataTypeException.class, () ->
                intValidator.validate("Test"));
    }

    @Test
    public void otherObjectThrowsException() {
        assertThrows(MismatchedDataTypeException.class, () ->
                intValidator.validate(new Object()));
    }

}