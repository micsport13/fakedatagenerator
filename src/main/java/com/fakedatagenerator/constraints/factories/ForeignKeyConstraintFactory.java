package com.fakedatagenerator.constraints.factories;

import com.fakedatagenerator.commands.DataManager;
import com.fakedatagenerator.constraints.Constraint;
import com.fakedatagenerator.constraints.ForeignKeyConstraint;
import com.fakedatagenerator.table.Table;
import com.fakedatagenerator.utils.Primitives;

import java.util.Map;

public class ForeignKeyConstraintFactory implements ConstraintFactory {
  private final DataManager dataManager;

  public ForeignKeyConstraintFactory(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  @Override
  public Constraint createConstraint(String constraintType, Map<String, Object> args) {
    Table foreignTable = dataManager.getTable((String) args.get("foreign_table"));
    String foreignColumnName = (String) args.get("foreign_column");
    return new ForeignKeyConstraint(foreignTable, foreignColumnName);
  }

  @Override
  public Map<String, Primitives> getOptions() {
    return Map.of("foreign_table", Primitives.STRING, "foreign_column", Primitives.STRING);
  }
}
