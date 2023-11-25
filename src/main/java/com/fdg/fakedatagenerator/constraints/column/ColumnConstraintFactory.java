package com.fdg.fakedatagenerator.constraints.column;

import com.fdg.fakedatagenerator.datatypes.DataType;
import com.fdg.fakedatagenerator.datatypes.VarcharDataType;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

import static com.fdg.fakedatagenerator.constraints.column.ColumnLevelConstraints.CHECK;
import static com.fdg.fakedatagenerator.constraints.column.ColumnLevelConstraints.NOT_NULL;

public class ColumnConstraintFactory {

  public static ColumnConstraint createConstraint(ColumnLevelConstraints constraintType) {
    return switch ( Objects.requireNonNull(constraintType)) {
      case CHECK -> throw new IllegalArgumentException(
          "When creating a column check constraint, you must provide parameters");
      case NOT_NULL -> new NotNullConstraint();
    };
  }

  public static <T extends Number> ColumnConstraint createConstraint(
          @NotNull DataType<T> dataType, T minValue, T maxValue) {
      return new ColumnCheckConstraint.Builder<>(dataType).withRange(minValue, maxValue).build();
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
    return new ColumnCheckConstraint.Builder<>(new VarcharDataType()) // TODO: Pass in varchar type?
        .withAcceptedValues(firstAcceptedValue, acceptedValues)
        .build();
  }
}
