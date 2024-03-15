package com.fakedatagenerator.constraints;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.exceptions.ForeignKeyConstraintException;
import com.fakedatagenerator.table.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

/** The type Foreign key constraint. */
@JsonSerialize(using = ForeignKeyConstraintSerializer.class)
@JsonDeserialize(using = ForeignKeyConstraintDeserializer.class)
public class ForeignKeyConstraint implements Constraint {
  @JsonIgnore private final Set<Object> foreignKeyValues = new HashSet<>();

  @JsonProperty("foreign_column")
  @Getter
  private final String foreignColumnName;

  @JsonIgnore @Getter private final Table foreignTable;

  /**
   * Instantiates a new Foreign key constraint.
   *
   * @param foreignTable the foreign multi
   * @param foreignColumnName the foreign single
   */
  public ForeignKeyConstraint(Table foreignTable, String foreignColumnName) {
    Column foreignColumn = foreignTable.getColumn(foreignColumnName);
    this.foreignTable = foreignTable;
    this.foreignColumnName = foreignColumn.getName();
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