package com.fdg.fakedatagenerator.constraints;

import com.fdg.fakedatagenerator.exceptions.NotNullConstraintException;

/** The type Not null constraint. */
public class NotNullConstraint implements Constraint {

  @Override
  public void validate(Object value) {
    if (value == null) {
      throw new NotNullConstraintException(": Value cannot be null");
      // to include at interface level
    }
  }

  @Override
  public int hashCode() {
    return 17 * 73;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    return o instanceof NotNullConstraint;
  }

  @Override
  public String toString() {
    return "Not Null";
  }
}