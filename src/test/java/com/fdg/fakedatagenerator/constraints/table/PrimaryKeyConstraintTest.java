package com.fdg.fakedatagenerator.constraints.table;

import com.fdg.fakedatagenerator.exceptions.PrimaryKeyConstraintException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The type Primary key constraint test.
 */
class PrimaryKeyConstraintTest {
    private PrimaryKeyConstraint primaryKeyConstraint;

    @BeforeEach
    void setUp() {
        this.primaryKeyConstraint = new PrimaryKeyConstraint();
    }

    @Test
    void validate_addValueNotInPrimaryKey_ThrowsNoException() {
        assertDoesNotThrow(() -> primaryKeyConstraint.validate(1));
    }

    @Test
    void validate_addMultipleValuesNotInPrimaryKey_ThrowsNoException() {
        assertDoesNotThrow(() -> primaryKeyConstraint.validate(1));
        assertDoesNotThrow(() -> primaryKeyConstraint.validate(2));
        assertDoesNotThrow(() -> primaryKeyConstraint.validate(3));
    }

    @Test
    void validate_addExistingValueToPrimaryKey_ThrowsPrimaryKeyConstraintException() {
        primaryKeyConstraint.validate(1);
        assertThrows(PrimaryKeyConstraintException.class, () -> primaryKeyConstraint.validate(1));
    }


}