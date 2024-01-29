package com.fdg.fakedatagenerator.constraints;

import com.fdg.fakedatagenerator.exceptions.NotNullConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NotNullConstraintTest {
  private final NotNullLevelConstraint notNullConstraint = new NotNullLevelConstraint();

  /** Null input throws not null constraint exception. */
  @Test
  public void validate_nullInput_ThrowsNotNullConstraintException() {
    Assertions.assertThrows(
        NotNullConstraintException.class, () -> notNullConstraint.validate(null));
  }

  /** Not null input throws no exception. */
  @Test
  public void notNullInputThrowsNoException() {
    Assertions.assertDoesNotThrow(() -> notNullConstraint.validate(1));
  }
}
