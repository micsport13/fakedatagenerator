package com.fdg.fakedatagenerator.table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.Constraint;
import com.fdg.fakedatagenerator.row.Row;
import com.fdg.fakedatagenerator.schema.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.*;
import lombok.Getter;

/** The type Table. */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Table {

  @Getter
  @JsonProperty("name")
  private final @NotNull String name;

  @Getter
  @JsonProperty("schema")
  private final Schema schema;

  @JsonIgnore private final transient List<Row> entities;

  @JsonCreator
  public Table(@JsonProperty("name") String name, @JsonProperty("schema") Schema schema) {
    this.name = name;
    this.schema = schema;
    this.entities = new ArrayList<>();
  }

  public void addConstraint(Constraint constraint, Column<?>... columns) {
    this.schema.addConstraint(constraint, columns);
  }

  /**
   * Gets row.
   *
   * @return the row
   */
  public List<Row> getEntities() {
    return new ArrayList<>(this.entities);
  }

  public List<Object> getColumnValues(String columnName) {
    List<Object> columnValues = new ArrayList<>();
    for (Row row : entities) {
      columnValues.add(row.getColumnValue(columnName));
    }
    return columnValues;
  }

  @JsonIgnore
  public Set<Column<?>> getColumns() {
    return this.getSchema().getColumns();
  }

  public Column<?> getColumn(String columnName) { // TODO: Figure out if optional is the right type
    return this.getSchema().getColumn(columnName);
  }

  /**
   * Add.
   *
   * @param row the row
   */
  public void add(Row row) {
    this.schema.validate(row);
    this.entities.add(row);
  }

  @Override
  public String toString() {
    return "Table: " + name + "\n" + schema.toString();
  }
}
