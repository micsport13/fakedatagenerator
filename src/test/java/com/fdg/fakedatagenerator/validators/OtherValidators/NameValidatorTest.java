package com.fdg.fakedatagenerator.validators.OtherValidators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NameValidatorTest {

    @Test
    public void validate_WithValidName_DoesNotThrowException() {
        assertDoesNotThrow(() -> NameValidator.validate("validName"));
        assertDoesNotThrow(() -> NameValidator.validate("valid_name"));
        assertDoesNotThrow(() -> NameValidator.validate("valid_name"));
        assertDoesNotThrow(() -> NameValidator.validate("validName123"));
    }

    @Test
    public void validate_WithInvalidNames_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> NameValidator.validate("1stColumnName"));
        assertThrows(IllegalArgumentException.class, () -> NameValidator.validate("@ColumnName"));
        assertThrows(IllegalArgumentException.class, () -> NameValidator.validate("# ColumnName"));
        assertThrows(IllegalArgumentException.class, () -> NameValidator.validate(" ColumnName"));
    }
}