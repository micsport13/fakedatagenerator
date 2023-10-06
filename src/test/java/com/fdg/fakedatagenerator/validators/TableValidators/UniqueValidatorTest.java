package com.fdg.fakedatagenerator.validators.TableValidators;

import com.fdg.fakedatagenerator.exceptions.UniqueConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The type Unique constraint test.
 */
class UniqueValidatorTest {
    /**
     * The Unique constraint.
     */
    UniqueValidator uniqueConstraint;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        this.uniqueConstraint = new UniqueValidator();
    }

    /**
     * Add unique value is valid.
     */
    @Test
    public void addUniqueValueIsValid() {
        Assertions.assertDoesNotThrow(() -> this.uniqueConstraint.validate(1));
    }

    /**
     * Add existing value throws unique constraint exception.
     */
    @Test
    public void addExistingValueThrowsUniqueConstraintException() {
        this.uniqueConstraint.validate(1);
        Assertions.assertThrows(UniqueConstraintException.class, () -> this.uniqueConstraint.validate(1));
    }

}