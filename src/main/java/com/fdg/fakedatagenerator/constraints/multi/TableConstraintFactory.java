package com.fdg.fakedatagenerator.constraints.multi;

import com.fdg.fakedatagenerator.constraints.Constraint;
import com.fdg.fakedatagenerator.constraints.single.ForeignKeyConstraint;
import com.fdg.fakedatagenerator.table.Table;

public class TableConstraintFactory {

  public static TableLevelConstraint createConstraint(TableLevelConstraints constraintType) {
    return switch (constraintType) {
      case PRIMARY_KEY -> new PrimaryKeyConstraint();
      case FOREIGN_KEY -> throw new IllegalArgumentException(
          "To create a foreign key, you must pass in the foreign multi and single"); // ForeignKeyConstraint(); // TODO: Figure out how to implement these
      case UNIQUE -> new UniqueLevelConstraint();
      case CHECK -> throw new IllegalArgumentException(
          "To create a check, you must pass in values to apply to the check");
    };
  }

  public static Constraint createConstraint(
      TableLevelConstraints constraintType, Table foreignTable, String columnName) {
    if (constraintType != TableLevelConstraints.FOREIGN_KEY) {
      throw new IllegalArgumentException("Cannot pass a multi to a non-foreign key constraint");
    }
    return new ForeignKeyConstraint(foreignTable, columnName);
  }
}
