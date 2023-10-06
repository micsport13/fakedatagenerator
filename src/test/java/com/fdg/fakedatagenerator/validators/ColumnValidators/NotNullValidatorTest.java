package com.fdg.fakedatagenerator.validators.ColumnValidators;

import com.fdg.fakedatagenerator.exceptions.NotNullConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NotNullValidatorTest {
    private NotNullValidator notNullValidator = new NotNullValidator();

    /**
     * Null input throws not null constraint exception.
     */
    @Test
    public void validate_nullInput_ThrowsNotNullConstraintException() {
        Assertions.assertThrows(NotNullConstraintException.class, () -> notNullValidator.validate(null));
    }

    /**
     * Not null input throws no exception.
     */
    @Test
    public void notNullInputThrowsNoException() {
        Assertions.assertDoesNotThrow(() -> notNullValidator.validate(1));
    }
}
