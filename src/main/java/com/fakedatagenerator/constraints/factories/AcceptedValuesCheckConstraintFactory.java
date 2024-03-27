package com.fakedatagenerator.constraints.factories;

import com.fakedatagenerator.constraints.AcceptedValuesCheckConstraint;
import com.fakedatagenerator.constraints.Constraint;
import com.fakedatagenerator.utils.Primitives;

import java.util.Map;

public class AcceptedValuesCheckConstraintFactory implements ConstraintFactory {

  @Override
  public Constraint createConstraint(String constraintType, Map<String, Object> args) {
    String[] acceptedValues = (String[]) args.get("accepted_values");
    return new AcceptedValuesCheckConstraint(acceptedValues);
  }

  @Override
  public Map<String, Primitives> getOptions() {
    return Map.of("accepted_values", Primitives.STRING_ARRAY);
  }
}
