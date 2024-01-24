package com.fdg.fakedatagenerator.constraints.single;

import com.fdg.fakedatagenerator.datatypes.DataType;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

public class ColumnConstraintFactory {

  public static ColumnLevelConstraint createConstraint(ColumnLevelConstraints constraintType) {
    return switch (Objects.requireNonNull(constraintType)) {
      case CHECK -> throw new IllegalArgumentException(
          "When creating a single check constraint, you must provide parameters");
      case NOT_NULL -> new NotNullLevelConstraint();
    };
  }

  public static <T extends Number> NumericCheckConstraint<T> createConstraint(
      @NotNull DataType<T> dataType, T minValue, T maxValue) {
    return new NumericCheckConstraint.Builder<T>().withRange(minValue, maxValue).build();
  }

  public static AcceptedValuesCheckConstraint createConstraint(
      String... acceptedValues) { // TODO: Incorrectly assigning values, potentially revisit this
    return new AcceptedValuesCheckConstraint(acceptedValues);
  }
}
