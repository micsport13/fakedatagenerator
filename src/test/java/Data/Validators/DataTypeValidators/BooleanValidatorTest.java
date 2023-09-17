package Data.Validators.DataTypeValidators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BooleanValidatorTest {
    private final BooleanValidator booleanValidator = new BooleanValidator();

    /**
     * Valid input throws no exception.
     */
// Testing Booleans
    @Test
    public void validInputThrowsNoException() {
        assertTrue(booleanValidator.validate(true));
    }


    /**
     * Input of zero is valid.
     */
// Testing Integers in place of booleans
    @Test
    public void inputOfZeroIsValid() {
        assertTrue(booleanValidator.validate(0));
    }

    /**
     * Input of one is valid.
     */
    @Test
    public void inputOfOneIsValid() {
        assertTrue(booleanValidator.validate(1));
    }

    /**
     * Input of true is valid.
     */
    @Test
    public void inputOfTrueIsValid() {
        assertTrue(booleanValidator.validate(true));
    }

    /**
     * Input of false is valid.
     */
    @Test
    public void inputOfFalseIsValid() {
        assertTrue(booleanValidator.validate(false));
    }
}