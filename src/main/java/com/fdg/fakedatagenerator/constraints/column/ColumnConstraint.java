package com.fdg.fakedatagenerator.constraints.column;

import com.fdg.fakedatagenerator.constraints.Constraint;

/** The interface Column constraint. */
public interface ColumnConstraint extends Constraint {
  boolean conflictsWith(ColumnConstraint other);
}
