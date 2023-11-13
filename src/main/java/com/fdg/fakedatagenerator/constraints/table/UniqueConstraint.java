package com.fdg.fakedatagenerator.constraints.table;

import com.fdg.fakedatagenerator.exceptions.UniqueConstraintException;

import java.util.HashSet;
import java.util.Set;

/** The type Unique constraint. */
public class UniqueConstraint implements TableConstraint {
  private final Set<Object> uniqueValues = new HashSet<>(); //

  private void addValue(Object value) {
    this.uniqueValues.add(value);
  }

  @Override
  public void validate(Object value) {
    if (this.uniqueValues.contains(value)) {
      throw new UniqueConstraintException(
          UniqueConstraint.class.getSimpleName()
              + ": Value already exists in the unique constraint");
    }
    this.addValue(value);
  }

  @Override
  public int hashCode() {
    return this.uniqueValues.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    return obj instanceof UniqueConstraint;
  }

  @Override
  public String toString() {
    return "Unique";
  }
}
