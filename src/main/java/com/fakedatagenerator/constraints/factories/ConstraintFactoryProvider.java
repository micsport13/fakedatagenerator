package com.fakedatagenerator.constraints.factories;

import com.fakedatagenerator.commands.DataManager;

public class ConstraintFactoryProvider {
  private final DataManager dataManager;

  public ConstraintFactoryProvider(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  public ConstraintFactory getConstraintFactory(String constraintType) {
    return switch (constraintType) {
      case "unique" -> new UniqueConstraintFactory();
      case "not_null" -> new NotNullConstraintFactory();
      case "foreign_key" -> new ForeignKeyConstraintFactory(dataManager);
      case "numeric_check" -> new NumericCheckConstraintFactory<>(Integer.class);
      case "primary_key" -> new PrimaryKeyConstraintFactory();
      case "accepted_values" -> new AcceptedValuesCheckConstraintFactory();
      case "table_check" -> new MultiColumnConstraintFactory(dataManager);
      default -> throw new IllegalArgumentException("Invalid constraint type: " + constraintType);
    };
  }
}
