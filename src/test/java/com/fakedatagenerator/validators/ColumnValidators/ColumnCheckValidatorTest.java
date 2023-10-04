package com.fakedatagenerator.validators.ColumnValidators;

import com.fakedatagenerator.exceptions.CheckConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The type Column check constraint test.
 */
class ColumnCheckValidatorTest {

    /**
     * Value below minimum check constraint throws exception.
     */
// Testing Minimum Values
    @Test
    public void validate_valueBelowMinimumCheckConstraint_ThrowsException() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder<>(Integer.class).withMinimumValue(0.0)
                .build();
        Assertions.assertThrows(CheckConstraintException.class, () ->
            columnCheckConstraint.validate(-1));
    }

    /**
     * Value at minimum check constraint throws no exception.
     */
    @Test
    public void validate_AtMinimumCheckConstraint_ThrowsNoException() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder<>(Integer.class).withMinimumValue(0)
                .build();
        assertDoesNotThrow(() -> columnCheckConstraint.validate(0));
    }

    @Test
    public void validate_BelowMinimumCheckConstraint_ThrowsException() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder<>(Integer.class).withMinimumValue(0)
                .build();
        assertThrows(CheckConstraintException.class, () -> columnCheckConstraint.validate(-1));
    }
    @Test
    public void validate_ExtremelySmallDoubleBelowMinimumCheckConstraint_ThrowsException() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder<>(Float.class).withMinimumValue(0f)
                .build();
        assertThrows(CheckConstraintException.class, () -> columnCheckConstraint.validate(-.00000000005f));
    }

    /**
     * Value above maximum check constraint throws exception.
     */
// Testing Maximum Values
    @Test
    public void validate_valueAboveMaximumCheckConstraint_ThrowsException() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder<>(Integer.class).withMaximumValue(10.0)
                .build();
        Assertions.assertThrows(CheckConstraintException.class, () ->  columnCheckConstraint.validate(11));
    }

    /**
     * Value at maximum check constraint throws no exception.
     */
    @Test
    public void validate_AtMaximumCheckConstraint_ThrowsNoException() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder<>(Integer.class).withMaximumValue(10)
                .build();
        assertDoesNotThrow(() -> columnCheckConstraint.validate(10));
    }

    /**
     * Value between minimum and maximum check constraint throws no exception.
     */
// Testing Ranged Values
    @Test
    public void validate_BetweenMinimumAndMaximumCheckConstraint_ThrowsNoException() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder<>(Integer.class).withRange(0, 10)
                .build();
        assertDoesNotThrow(() -> columnCheckConstraint.validate(5));
    }

    /**
     * Value not in accepted values throws exception.
     */
// Testing Accepted Values
    @Test
    public void validate_NotInAcceptedValues_ThrowsException() {
        ColumnCheckValidator
                columnCheckConstraint =
                new ColumnCheckValidator.CheckConstraintBuilder<>(String.class).withAcceptedValues("Test", "Test1")
                        .build();
        Assertions.assertThrows(CheckConstraintException.class, () -> columnCheckConstraint.validate("Test3"));
    }

    /**
     * Value in accepted values throws no exception.
     */
    @Test
    public void validate_ValueInAcceptedValues_ThrowsNoException() {
        ColumnCheckValidator
                columnCheckConstraint =
                new ColumnCheckValidator.CheckConstraintBuilder<>(String.class).withAcceptedValues("Test", "Test1")
                        .build();
        Assertions.assertDoesNotThrow(() -> columnCheckConstraint.validate("Test"));
    }

    /**
     * Multiple check constraints with same minimum are equal.
     */
// Comparing Constraints
    @Test
    public void equals_multipleCheckConstraintsWithSameMinimum_AreEqual() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder<>(Integer.class).withMinimumValue(0)
                .withMaximumValue(10)
                .build();
        ColumnCheckValidator
                columnCheckConstraint1 = new ColumnCheckValidator.CheckConstraintBuilder<>(Integer.class).withMinimumValue(0)
                .withMaximumValue(10)
                .build();
        Assertions.assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with same maximum are equal.
     */
    @Test
    public void equals_multipleCheckConstraintsWithSameMaximum_AreEqual() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder<>(Integer.class).withMaximumValue(10)
                .build();
        ColumnCheckValidator
                columnCheckConstraint1 = new ColumnCheckValidator.CheckConstraintBuilder<>(Integer.class).withMaximumValue(10)
                .build();
        Assertions.assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with same range are equal.
     */
    @Test
    public void equals_multipleCheckConstraintsWithSameRange_AreEqual() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder<>(Integer.class).withMinimumValue(0)
                .withMaximumValue(10)
                .build();
        ColumnCheckValidator
                columnCheckConstraint1 = new ColumnCheckValidator.CheckConstraintBuilder<>(Integer.class).withMinimumValue(0)
                .withMaximumValue(10)
                .build();
        Assertions.assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with different ranges are not equal.
     */
    @Test
    public void equals_multipleCheckConstraintsWithDifferentRanges_AreNotEqual() {
        ColumnCheckValidator
                columnCheckConstraint = new ColumnCheckValidator.CheckConstraintBuilder<>(Integer.class).withMinimumValue(0)
                .withMaximumValue(10)
                .build();
        ColumnCheckValidator
                columnCheckConstraint1 = new ColumnCheckValidator.CheckConstraintBuilder<>(Integer.class).withMinimumValue(0)
                .withMaximumValue(8)
                .build();
        Assertions.assertNotEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with same accepted values are equal.
     */
    @Test
    public void equals_multipleCheckConstraintsWithSameAcceptedValues_AreEqual() {
        ColumnCheckValidator
                columnCheckConstraint =
                new ColumnCheckValidator.CheckConstraintBuilder<>(String.class).withAcceptedValues("Test", "Test1")
                        .build();
        ColumnCheckValidator
                columnCheckConstraint1 =
                new ColumnCheckValidator.CheckConstraintBuilder<>(String.class).withAcceptedValues("Test", "Test1")
                        .build();
        Assertions.assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with different accepted values are not equal.
     */
    @Test
    public void equals_multipleCheckConstraintsWithDifferentAcceptedValues_AreNotEqual() {
        ColumnCheckValidator
                columnCheckConstraint =
                new ColumnCheckValidator.CheckConstraintBuilder<>(String.class).withAcceptedValues("Test", "Test1")
                        .build();
        ColumnCheckValidator
                columnCheckConstraint1 = new ColumnCheckValidator.CheckConstraintBuilder<>(String.class).withAcceptedValues("Test")
                .build();
        Assertions.assertNotEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    /**
     * Multiple check constraints with different ordered accepted values are equal.
     */
    @Test
    public void equals_multipleCheckConstraintsWithDifferentOrderedAcceptedValues_AreEqual() {
        ColumnCheckValidator
                columnCheckConstraint =
                new ColumnCheckValidator.CheckConstraintBuilder<>(String.class).withAcceptedValues("Test", "Test1")
                        .build();
        ColumnCheckValidator
                columnCheckConstraint1 =
                new ColumnCheckValidator.CheckConstraintBuilder<>(String.class).withAcceptedValues("Test1", "Test")
                        .build();
        Assertions.assertEquals(columnCheckConstraint, columnCheckConstraint1);
    }

    @Test
    public void build_WithBothRangeAndAcceptedValues_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new ColumnCheckValidator.CheckConstraintBuilder<>(String.class).withRange(0,1).withAcceptedValues("TEst").build());
    }


}