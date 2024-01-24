package com.fdg.fakedatagenerator.constraints.multi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fdg.fakedatagenerator.column.Column;

/** The type Table check constraint. */
public class MultiColumnCheckConstraint implements TableLevelConstraint {
  @JsonProperty("first_column")
  private final Column<?> firstColumn;  // TODO: Allow comparison between columns

  @JsonProperty("second_column")
  private final Column<?> secondColumn;

  public MultiColumnCheckConstraint(Column<?> firstColumn, Column<?> secondColumn) {
    this.firstColumn = firstColumn;
    this.secondColumn = secondColumn;
  }

  @Override
  public void validate(Object value) {
    // TODO: handle the checking
    // Should correspond to making sure an entity is created with the correct values in all of the
    // objects.

    //  this.currentTable.getColumnValues(this.otherColumn.getName());
  }

  @Override
  public String toString() {
    return "Check"; // TODO: Update with attributes once defined
  }
}
