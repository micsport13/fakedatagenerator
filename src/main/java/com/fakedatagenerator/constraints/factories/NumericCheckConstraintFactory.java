package com.fakedatagenerator.constraints.factories;

import com.fakedatagenerator.constraints.Constraint;
import com.fakedatagenerator.constraints.NumericCheckConstraint;
import com.fakedatagenerator.utils.Primitives;
import java.util.Map;

public class NumericCheckConstraintFactory<T extends Number> implements ConstraintFactory {

  private final Class<T> numberType;

  public NumericCheckConstraintFactory(Class<T> numberType) {
    this.numberType = numberType;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Constraint createConstraint(String constraintType, Map<String, Object> args) {
    T minValue = (T) args.get("min_value");
    T maxValue = (T) args.get("max_value");
    return new NumericCheckConstraint.Builder<>().withRange(minValue, maxValue).build();
  }

  @Override
  public Map<String, Primitives> getOptions() {
    return switch (Primitives.valueOf(numberType.getSimpleName())) {
      case BYTE -> Map.of("min_value", Primitives.BYTE, "max_value", Primitives.BYTE);
      case SHORT -> Map.of("min_value", Primitives.SHORT, "max_value", Primitives.SHORT);
      case INT -> Map.of("min_value", Primitives.INT, "max_value", Primitives.INT);
      case LONG -> Map.of("min_value", Primitives.LONG, "max_value", Primitives.LONG);
      case FLOAT -> Map.of("min_value", Primitives.FLOAT, "max_value", Primitives.FLOAT);
      case DOUBLE -> Map.of("min_value", Primitives.DOUBLE, "max_value", Primitives.DOUBLE);
      default ->
          throw new IllegalArgumentException(
              "Unsupported number type: " + numberType.getSimpleName());
    };
  }
}
