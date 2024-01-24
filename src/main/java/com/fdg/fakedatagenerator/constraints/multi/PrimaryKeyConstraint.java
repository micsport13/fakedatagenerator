package com.fdg.fakedatagenerator.constraints.multi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.exceptions.PrimaryKeyConstraintException;
import java.util.HashSet;
import java.util.Set;

/** The type Primary key constraint. */
public class PrimaryKeyConstraint implements TableLevelConstraint {
  private final Set<Column<?>> primaryKeyColumns;
  @JsonIgnore private final Set<Object> primaryKeyValues = new HashSet<>();

  public PrimaryKeyConstraint(Column<?>... primaryKeyColumns) {
    this.primaryKeyColumns = new HashSet<>(Set.of(primaryKeyColumns));
  }

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
