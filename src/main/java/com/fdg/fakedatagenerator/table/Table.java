package com.fdg.fakedatagenerator.table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.table.TableConstraint;
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

  @JsonIgnore private final transient List<Row> entities = new ArrayList<>();

  @JsonCreator
  public Table(@JsonProperty("name") String name, @JsonProperty("schema") Schema schema) {
    this.name = name;
    this.schema = schema;
  }

  /**
   * Add table constraint.
   *
   * @param column the column
   * @param tableConstraints the table constraints
   */
  public void addTableConstraint(Column<?> column, TableConstraint... tableConstraints) {
    var schemaColumns = this.schema.getTableConstraints();
    if (schemaColumns.containsKey(column)) {
      Set<TableConstraint> columnConstraints = schemaColumns.get(column);
      if (columnConstraints.containsAll(Arrays.asList(tableConstraints))) {
        System.out.println("Constraint already exists for column " + column.getName());
      } else {
        this.schema
            .getTableConstraints()
            .get(column)
            .addAll(
                Set.of(
                    Objects.requireNonNull(tableConstraints, "Must provide a table constraint")));
      }
    }
  }

  /**
   * Gets row.
   *
   * @return the row
   */
  public List<Row> getEntities() {
    return new ArrayList<>(entities);
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
    return this.getSchema().getColumn(columnName).get();
  }

  /**
   * Add.
   *
   * @param row the row
   */
  public void add(Row row) {
    if (isValidEntity(row)) { // TODO: Fix if schema doesn't match row
      entities.add(row);
    } else {
      throw new IllegalArgumentException("Row is not valid for table " + this.name);
    }
  }

  /**
   * Is valid row boolean.
   *
   * @param row the row
   * @return the boolean
   */
  public boolean isValidEntity(Row row) {
    for (Map.Entry<Column<?>, Set<TableConstraint>> tableConstraints :
        this.schema.getTableConstraints().entrySet()) {
      if (tableConstraints.getValue() != null) {
        tableConstraints
            .getValue()
            .forEach(
                constraint ->
                    constraint.validate(
                        row.getColumnValueMapping().get(tableConstraints.getKey())));
      }
    }
    return this.getColumns().containsAll(row.getColumnValueMapping().keySet());
  }

  @Override
  public String toString() {
    return "Table: " + name + "\n" + schema.toString();
  }
}
