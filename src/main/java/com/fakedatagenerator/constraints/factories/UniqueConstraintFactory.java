package com.fakedatagenerator.constraints.factories;

import com.fakedatagenerator.constraints.Constraint;
import com.fakedatagenerator.constraints.UniqueConstraint;
import com.fakedatagenerator.utils.Primitives;
import java.util.Collections;
import java.util.Map;

public class UniqueConstraintFactory implements ConstraintFactory {
  @Override
  public Constraint createConstraint(String constraintType, Map<String, Object> args) {
    return new UniqueConstraint();
  }

  @Override
  public Map<String, Primitives> getOptions() {
    return Collections.emptyMap();
  }
}
