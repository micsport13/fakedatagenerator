package com.fakedatagenerator.constraints;

import com.fakedatagenerator.exceptions.PrimaryKeyConstraintException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

/** The type Primary key constraint. */
public class PrimaryKeyConstraint implements Constraint {
  @JsonIgnore private final Set<Object> primaryKeyValues = new HashSet<>();

  private void addValue(Object value) {
    primaryKeyValues.add(value);
  }

  @Override
  public void validate(Object value) {
    if (value == null) {
      throw new PrimaryKeyConstraintException("Primary key cannot be null");
    } else if (primaryKeyValues.contains(value)) {
      throw new PrimaryKeyConstraintException("Primary key must be unique.yml");
    }
    this.addValue(value);
  }

  @Override
  public int hashCode() {
    return 17 * 31;
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
