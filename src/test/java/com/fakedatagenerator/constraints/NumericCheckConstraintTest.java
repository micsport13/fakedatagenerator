package com.fakedatagenerator.constraints;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fakedatagenerator.exceptions.CheckConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/** The type Column check constraint test. */
class NumericCheckConstraintTest {

  /** Value below minimum check constraint throws exception. */
  // Testing Minimum Values
  @Test
  public void validate_valueBelowMinimumCheckConstraint_ThrowsException() {
    NumericCheckConstraint<Double> columnCheckConstraint =
        new NumericCheckConstraint.Builder<Double>().withMinimumValue(0.0).build();
    Assertions.assertThrows(
        CheckConstraintException.class, () -> columnCheckConstraint.validate(-1));
  }

  /** Value at minimum check constraint throws no exception. */
  @Test
  public void validate_AtMinimumCheckConstraint_ThrowsNoException() {
    NumericCheckConstraint<Integer> columnCheckConstraint =
        new NumericCheckConstraint.Builder<Integer>().withMinimumValue(0).build();
    assertDoesNotThrow(() -> columnCheckConstraint.validate(0));
  }

  @Test
  public void validate_BelowMinimumCheckConstraint_ThrowsException() {
    NumericCheckConstraint<Integer> columnCheckConstraint =
        new NumericCheckConstraint.Builder<Integer>().withMinimumValue(0).build();
    assertThrows(CheckConstraintException.class, () -> columnCheckConstraint.validate(-1));
  }

  @Test
  public void validate_ExtremelySmallDoubleBelowMinimumCheckConstraint_ThrowsException() {
    NumericCheckConstraint<Float> columnCheckConstraint =
        new NumericCheckConstraint.Builder<Float>().withMinimumValue(0f).build();
    assertThrows(
        CheckConstraintException.class, () -> columnCheckConstraint.validate(-.00000000005f));
  }

  /** Value above maximum check constraint throws exception. */
  // Testing Maximum Values
  @Test
  public void validate_valueAboveMaximumCheckConstraint_ThrowsException() {
    NumericCheckConstraint<Double> columnCheckConstraint =
        new NumericCheckConstraint.Builder<Double>().withMaximumValue(10.0).build();
    Assertions.assertThrows(
        CheckConstraintException.class, () -> columnCheckConstraint.validate(11));
  }

  /** Value at maximum check constraint throws no exception. */
  @Test
  public void validate_AtMaximumCheckConstraint_ThrowsNoException() {
    NumericCheckConstraint<Integer> columnCheckConstraint =
        new NumericCheckConstraint.Builder<Integer>().withMaximumValue(10).build();
    assertDoesNotThrow(() -> columnCheckConstraint.validate(10));
  }

  /** Value between minimum and maximum check constraint throws no exception. */
  // Testing Ranged Values
  @Test
  public void validate_BetweenMinimumAndMaximumCheckConstraint_ThrowsNoException() {
    NumericCheckConstraint<Integer> columnCheckConstraint =
        new NumericCheckConstraint.Builder<Integer>().withRange(0, 10).build();
    assertDoesNotThrow(() -> columnCheckConstraint.validate(5));
  }

  /** Value not in accepted values throws exception. */
  // Testing Accepted Values
  @Test
  public void validate_NotInAcceptedValues_ThrowsException() {
    AcceptedValuesCheckConstraint columnCheckConstraint =
        new AcceptedValuesCheckConstraint("Test", "Test1");
    Assertions.assertThrows(
        CheckConstraintException.class, () -> columnCheckConstraint.validate("Test3"));
  }

  /** Multiple check constraints with same minimum are equal. */
  // Comparing Constraints
  @Test
  public void equals_multipleCheckConstraintsWithSameMinimum_AreEqual() {
    NumericCheckConstraint<Integer> columnCheckConstraint =
        new NumericCheckConstraint.Builder<Integer>()
            .withMinimumValue(0)
            .withMaximumValue(10)
            .build();
    NumericCheckConstraint<Integer> columnCheckConstraint1 =
        new NumericCheckConstraint.Builder<Integer>()
            .withMinimumValue(0)
            .withMaximumValue(10)
            .build();
    Assertions.assertEquals(columnCheckConstraint, columnCheckConstraint1);
  }

  /** Multiple check constraints with same maximum are equal. */
  @Test
  public void equals_multipleCheckConstraintsWithSameMaximum_AreEqual() {
    NumericCheckConstraint<Integer> columnCheckConstraint =
        new NumericCheckConstraint.Builder<Integer>().withMaximumValue(10).build();
    NumericCheckConstraint<Integer> columnCheckConstraint1 =
        new NumericCheckConstraint.Builder<Integer>().withMaximumValue(10).build();
    Assertions.assertEquals(columnCheckConstraint, columnCheckConstraint1);
  }

  /** Multiple check constraints with same range are equal. */
  @Test
  public void equals_multipleCheckConstraintsWithSameRange_AreEqual() {
    NumericCheckConstraint<Integer> columnCheckConstraint =
        new NumericCheckConstraint.Builder<Integer>()
            .withMinimumValue(0)
            .withMaximumValue(10)
            .build();
    NumericCheckConstraint<Integer> columnCheckConstraint1 =
        new NumericCheckConstraint.Builder<Integer>()
            .withMinimumValue(0)
            .withMaximumValue(10)
            .build();
    Assertions.assertEquals(columnCheckConstraint, columnCheckConstraint1);
  }

  /** Multiple check constraints with different ranges are not equal. */
  @Test
  public void equals_multipleCheckConstraintsWithDifferentRanges_AreNotEqual() {
    NumericCheckConstraint<Integer> columnCheckConstraint =
        new NumericCheckConstraint.Builder<Integer>()
            .withMinimumValue(0)
            .withMaximumValue(10)
            .build();
    NumericCheckConstraint<Integer> columnCheckConstraint1 =
        new NumericCheckConstraint.Builder<Integer>()
            .withMinimumValue(0)
            .withMaximumValue(8)
            .build();
    Assertions.assertNotEquals(columnCheckConstraint, columnCheckConstraint1);
  }
}
