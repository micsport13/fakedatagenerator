package com.fakedatagenerator.constraints;

import com.fakedatagenerator.exceptions.UniqueConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** The type Unique constraint test. */
class UniqueConstraintTest {
  /** The Unique constraint. */
  UniqueConstraint uniqueConstraint;

  /** Sets up. */
  @BeforeEach
  public void setUp() {
    this.uniqueConstraint = new UniqueConstraint();
  }

  /** Add unique.yml value is valid. */
  @Test
  public void validate_addUniqueValue_IsValid() {
    Assertions.assertDoesNotThrow(() -> this.uniqueConstraint.validate(1));
  }

  /** Add existing value throws unique.yml constraint exception. */
  @Test
  public void validate_addExistingValue_ThrowsUniqueConstraintException() {
    this.uniqueConstraint.validate(1);
    Assertions.assertThrows(
        UniqueConstraintException.class, () -> this.uniqueConstraint.validate(1));
  }

  @Test
  public void validate_addConcatenatedString_ThrowsUniqueConstraintException() {
    String val = "test;test1";
    this.uniqueConstraint.validate(val);
    Assertions.assertThrows(
        UniqueConstraintException.class, () -> this.uniqueConstraint.validate(val));
  }
}