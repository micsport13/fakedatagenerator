package com.fakedatagenerator.constraints.factories;

import com.fakedatagenerator.constraints.Constraint;
import com.fakedatagenerator.constraints.PrimaryKeyConstraint;
import com.fakedatagenerator.utils.Primitives;

import java.util.Collections;
import java.util.Map;

public class PrimaryKeyConstraintFactory implements ConstraintFactory {
  @Override
  public Constraint createConstraint(String constraintType, Map<String, Object> args) {
    return new PrimaryKeyConstraint();
  }

  @Override
  public Map<String, Primitives> getOptions() {
    return Collections.emptyMap();
  }
}
