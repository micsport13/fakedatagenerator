package com.fdg.fakedatagenerator.constraints;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

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

  @Test
  public void validate_withNameLongerThan31Characters_ThrowsIllegalArgumentException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> NameValidator.validate("ThisNameIsLongerThan31Characters"));
  }
}
