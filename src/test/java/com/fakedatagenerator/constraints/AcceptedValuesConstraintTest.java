package com.fakedatagenerator.constraints;

import com.fakedatagenerator.exceptions.CheckConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AcceptedValuesConstraintTest {

  @Test
  public void constructor_NoAcceptedValues_ThrowsIllegalArgumentException() {
    Assertions.assertThrows(IllegalArgumentException.class, AcceptedValuesCheckConstraint::new);
  }

  @Test
  public void constructor_nullAcceptedValues_ThrowsIllegalArgumentException() {
    Assertions.assertThrows(IllegalArgumentException.class, AcceptedValuesCheckConstraint::new);
  }

  /** Value in accepted values throws no exception. */
  @Test
  public void validate_ValueInAcceptedValues_ThrowsNoException() {
    AcceptedValuesCheckConstraint columnCheckConstraint =
        new AcceptedValuesCheckConstraint("Test", "Test1");
    Assertions.assertDoesNotThrow(() -> columnCheckConstraint.validate("Test"));
  }

  @Test
  public void validate_caseSensitiveNotInAcceptedValues_ThrowsException() {
    AcceptedValuesCheckConstraint columnCheckConstraint =
        new AcceptedValuesCheckConstraint("Test", "Test1");
    Assertions.assertThrows(
        CheckConstraintException.class, () -> columnCheckConstraint.validate("test"));
  }

  @Test
  public void validate_withNonStringArgument_ThrowsException() {
    AcceptedValuesCheckConstraint columnCheckConstraint =
        new AcceptedValuesCheckConstraint("Test", "Test1");
    Assertions.assertThrows(
        CheckConstraintException.class, () -> columnCheckConstraint.validate(1));
  }
}
