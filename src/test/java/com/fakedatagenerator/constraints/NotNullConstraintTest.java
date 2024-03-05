package com.fakedatagenerator.constraints;

import com.fakedatagenerator.exceptions.NotNullConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NotNullConstraintTest {
  private final NotNullConstraint notNullConstraint = new NotNullConstraint();

  /** Null input throws not null constraint exception. */
  @Test
  public void validate_nullInput_ThrowsNotNullConstraintException() {
    Assertions.assertThrows(
        NotNullConstraintException.class, () -> notNullConstraint.validate(null));
  }

  /** Not null input throws no exception. */
  @Test
  public void validate_notNullInput_ThrowsNoException() {
    Assertions.assertDoesNotThrow(() -> notNullConstraint.validate(1));
  }
}
