package com.fakedatagenerator.constraints;

import com.fakedatagenerator.exceptions.PrimaryKeyConstraintException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.log4j.Log4j2;

/** The type Primary key constraint. */
@Log4j2
public class PrimaryKeyConstraint implements Constraint, StateConstraint {
  @JsonIgnore private final Set<Object> primaryKeyValues = new HashSet<>();

  private void addValue(Object value) {
    primaryKeyValues.add(value);
  }

  @Override
  public void validate(Object value) {
    if (value == null) {
      throw new PrimaryKeyConstraintException("Primary key cannot be null");
    } else if (primaryKeyValues.contains(value)) {
      log.error("Violation value:{}", value);
      throw new PrimaryKeyConstraintException("Primary key must be unique.");
    }
    this.addValue(value);
  }

  @Override
  public void reset() {
    this.primaryKeyValues.clear();
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
