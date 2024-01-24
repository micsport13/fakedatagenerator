package com.fdg.fakedatagenerator.constraints.single;

import com.fdg.fakedatagenerator.exceptions.CheckConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AcceptedValuesConstraintTest {

  @Test
  public void constructor_NoAcceptedValues_ThrowsIllegalArgumentException() {
    Assertions.assertThrows(IllegalArgumentException.class, AcceptedValuesCheckConstraint::new);
  }

  @Test
  public void constructor_nullAcceptedValues_ThrowsIllegalArgumentException() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> new AcceptedValuesCheckConstraint(null));
  }

  /** Multiple check constraints with same accepted values are equal. */
  @Test
  public void equals_multipleCheckConstraintsWithSameAcceptedValues_AreEqual() {
    AcceptedValuesCheckConstraint acceptedValuesCheckConstraint =
        new AcceptedValuesCheckConstraint("Test", "Test1");
    AcceptedValuesCheckConstraint acceptedValuesCheckConstraint1 =
        new AcceptedValuesCheckConstraint("Test", "Test1");
    Assertions.assertEquals(acceptedValuesCheckConstraint, acceptedValuesCheckConstraint1);
  }

  /** Multiple check constraints with different accepted values are not equal. */
  @Test
  public void equals_multipleCheckConstraintsWithDifferentAcceptedValues_AreNotEqual() {
    AcceptedValuesCheckConstraint columnCheckConstraint =
        new AcceptedValuesCheckConstraint("Test", "Test1");
    AcceptedValuesCheckConstraint columnCheckConstraint1 =
        new AcceptedValuesCheckConstraint("Test");
    Assertions.assertNotEquals(columnCheckConstraint, columnCheckConstraint1);
  }

  /** Multiple check constraints with different ordered accepted values are equal. */
  @Test
  public void equals_multipleCheckConstraintsWithDifferentOrderedAcceptedValues_AreEqual() {
    AcceptedValuesCheckConstraint columnCheckConstraint =
        new AcceptedValuesCheckConstraint("Test", "Test1");
    AcceptedValuesCheckConstraint columnCheckConstraint1 =
        new AcceptedValuesCheckConstraint("Test1", "Test");
    Assertions.assertEquals(columnCheckConstraint, columnCheckConstraint1);
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
}
