package com.fakedatagenerator.constraints.factories;

import com.fakedatagenerator.constraints.Constraint;
import com.fakedatagenerator.utils.FactoryOptions;

import java.util.Map;

public interface ConstraintFactory extends FactoryOptions {
  Constraint createConstraint(String constraintType, Map<String, Object> args);
}
