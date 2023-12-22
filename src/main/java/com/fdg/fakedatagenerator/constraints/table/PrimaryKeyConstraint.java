package com.fdg.fakedatagenerator.constraints.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fdg.fakedatagenerator.exceptions.PrimaryKeyConstraintException;
import java.util.HashSet;
import java.util.Set;

/** The type Primary key constraint. */
public class PrimaryKeyConstraint implements TableConstraint {
  @JsonIgnore private final Set<Object> primaryKeyValues = new HashSet<>();

  private void addValue(Object value) {
    primaryKeyValues.add(value);
  }

  @Override
  public void validate(Object value) {
    if (value == null) {
      throw new PrimaryKeyConstraintException("Primary key cannot be null");
    } else if (primaryKeyValues.contains(value)) {
      throw new PrimaryKeyConstraintException("Primary key must be unique");
    }
    this.addValue(value);
  }

  @Override
  public int hashCode() {
    return 17;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof PrimaryKeyConstraint;
  }

  @Override
  public String toString() {
    return "Primary Key";
  }
}
