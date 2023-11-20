package com.fdg.fakedatagenerator.constraints.column;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fdg.fakedatagenerator.exceptions.NotNullConstraintException;
import com.fdg.fakedatagenerator.serializers.constraints.column.NotNullConstraintSerializer;

/** The type Not null constraint. */
@JsonSerialize(using = NotNullConstraintSerializer.class)
public class NotNullConstraint implements ColumnConstraint {
  @Override
  public void validate(Object value) {
    if (value == null) {
      throw new NotNullConstraintException(": Value cannot be null");
      // to include at interface level
    }
  }

  @Override
  public int hashCode() {
    return 79 * 31;
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

  @Override
  public boolean conflictsWith(ColumnConstraint other) {
    return false;
  }
}
