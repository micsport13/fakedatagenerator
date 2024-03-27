package com.fakedatagenerator.constraints.factories;

import com.fakedatagenerator.commands.DataManager;
import com.fakedatagenerator.constraints.Constraint;
import com.fakedatagenerator.utils.Primitives;
import java.util.Map;

public class MultiColumnConstraintFactory implements ConstraintFactory {
  private final DataManager dataManager;

  public MultiColumnConstraintFactory(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  @Override
  public Constraint createConstraint(String constraintType, Map<String, Object> args) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Map<String, Primitives> getOptions() {
    throw new UnsupportedOperationException();
  }
}
