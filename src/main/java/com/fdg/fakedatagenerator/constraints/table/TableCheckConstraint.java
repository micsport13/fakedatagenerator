package com.fdg.fakedatagenerator.constraints.table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.table.Table;

/** The type Table check constraint. */
public class TableCheckConstraint implements TableConstraint {
  @JsonProperty("otherColumn")
  private final Column otherColumn;

  @JsonProperty("currentTable")
  private final Table currentTable;

  public TableCheckConstraint(Table table, Column tableColumn) {
    this.otherColumn = tableColumn;
    this.currentTable = table;
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
