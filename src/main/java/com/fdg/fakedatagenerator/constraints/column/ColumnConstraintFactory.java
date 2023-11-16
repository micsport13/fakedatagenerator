package com.fdg.fakedatagenerator.constraints.column;

import com.fdg.fakedatagenerator.constraints.ColumnLevelConstraints;
import com.fdg.fakedatagenerator.datatypes.DataType;
import com.fdg.fakedatagenerator.datatypes.VarcharDataType;

public class ColumnConstraintFactory {

  public static ColumnConstraint createConstraint(ColumnLevelConstraints constraintType) {
    return switch (constraintType) {
      case CHECK -> throw new IllegalArgumentException(
          "When creating a column check constraint, you must provide parameters");
      case NOT_NULL -> new NotNullConstraint();
      default -> throw new IllegalArgumentException("Invalid constraint type");
    };
  }

  public static <T extends Number> ColumnConstraint createConstraint(
      DataType<T> dataType, T minValue, T maxValue) {
    if (dataType == null) {
      throw new IllegalArgumentException("Data type cannot be null");
    }
    if (minValue != null && maxValue != null) {
      return new ColumnCheckConstraint.Builder<>(dataType).withRange(minValue, maxValue).build();
    }
    if (minValue != null) {
      return new ColumnCheckConstraint.Builder<>(dataType).withMinimumValue(minValue).build();
    }
    if (maxValue != null) {
      return new ColumnCheckConstraint.Builder<>(dataType).withMaximumValue(maxValue).build();
    }
    throw new IllegalArgumentException("Unable to create numeric check constraint");
  }

  @SafeVarargs
  public static <U extends String> ColumnConstraint createConstraint(
      U firstAcceptedValue,
      U...
          acceptedValues) { // TODO: Incorrectly assigning values, potentially revisit this
                            // constructor
    if (firstAcceptedValue == null) {
      throw new IllegalArgumentException("Must provide at least one acceptable value");
    }
    return new ColumnCheckConstraint.Builder<>(new VarcharDataType())
        .withAcceptedValues(firstAcceptedValue, acceptedValues)
        .build();
  }
}
