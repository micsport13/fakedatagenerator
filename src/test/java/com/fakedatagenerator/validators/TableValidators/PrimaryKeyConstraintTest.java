package com.fakedatagenerator.validators.TableValidators;

import com.fakedatagenerator.exceptions.PrimaryKeyConstraintException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The type Primary key constraint test.
 */
class PrimaryKeyConstraintTest {
    private PrimaryKeyValidator primaryKeyValidator;

    @BeforeEach
    void setUp() {
        this.primaryKeyValidator = new PrimaryKeyValidator();
    }

    @Test
    void validate_addValueNotInPrimaryKey_ThrowsNoException() {
        assertDoesNotThrow(() -> primaryKeyValidator.validate(1));
    }

    @Test
    void validate_addMultipleValuesNotInPrimaryKey_ThrowsNoException() {
        assertDoesNotThrow(() -> primaryKeyValidator.validate(1));
        assertDoesNotThrow(() -> primaryKeyValidator.validate(2));
        assertDoesNotThrow(() -> primaryKeyValidator.validate(3));
    }

    @Test
    void validate_addExistingValueToPrimaryKey_ThrowsPrimaryKeyConstraintException() {
        primaryKeyValidator.validate(1);
        assertThrows(PrimaryKeyConstraintException.class, () -> primaryKeyValidator.validate(1));
    }


}