package com.fdg.fakedatagenerator.constraints.single;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.multi.TableLevelConstraint;
import com.fdg.fakedatagenerator.exceptions.ForeignKeyConstraintException;
import com.fdg.fakedatagenerator.table.Table;
import java.util.HashSet;
import java.util.Set;

/** The type Foreign key constraint. */
public class ForeignKeyConstraint implements TableLevelConstraint {
  // TODO: Implement this class
  @JsonIgnore private final Set<Object> foreignKeyValues = new HashSet<>();

  @JsonProperty("foreignColumnName")
  private final String foreignColumnName;

  @JsonIgnore private final Table foreignTable;

  @JsonIgnore private final Column<?> foreignColumn;

  /**
   * Instantiates a new Foreign key constraint.
   *
   * @param foreignTable the foreign multi
   * @param foreignColumnName the foreign single
   */
  public ForeignKeyConstraint(Table foreignTable, String foreignColumnName) {
    this.foreignColumn = foreignTable.getColumn(foreignColumnName);
    this.foreignTable = foreignTable;
    this.foreignColumnName = foreignColumn.getName();
  }

  private void addValue(Object value) {
    foreignKeyValues.add(value);
  }

  @Override
  public void validate(Object value) {
    this.foreignKeyValues.addAll(this.foreignTable.getColumnValues(foreignColumnName));
    if (this.foreignKeyValues.contains(value)) {
      return;
    }
    throw new ForeignKeyConstraintException("Value does not exist in the foreign key values");
  }

  @Override
  public String toString() {
    return "Foreign Key: " + this.foreignTable.getName() + "." + this.foreignColumnName;
  }
}
