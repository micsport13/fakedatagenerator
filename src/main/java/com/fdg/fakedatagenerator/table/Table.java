package com.fdg.fakedatagenerator.table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.table.TableConstraint;
import com.fdg.fakedatagenerator.row.Row;
import com.fdg.fakedatagenerator.schema.Schema;
import com.fdg.fakedatagenerator.serializers.table.TableDeserializer;
import com.fdg.fakedatagenerator.serializers.table.TableSerializer;
import jakarta.validation.constraints.NotNull;
import java.util.*;
import lombok.Getter;

/** The type Table. */
@JsonSerialize(using = TableSerializer.class)
@JsonDeserialize(using = TableDeserializer.class)
public class Table {

  @Getter private final @NotNull String name;

  @Getter private final Schema schema;
  private final transient List<Row> entities = new ArrayList<>();

  public Table(String name, Schema schema) {
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
      columnValues.add(row.getValue(columnName));
    }
    return columnValues;
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

  public void addColumn(Column<?> column, TableConstraint... tableConstraints) {
    this.schema.addColumn(column, tableConstraints);
  }

  public void dropColumn(String columnName) {
    Optional<Column<?>> column = this.schema.getColumn(columnName);
    if (column.isPresent()) {
      this.schema.getTableConstraints().remove(column.get());
      this.entities.forEach(row -> row.getColumnValueMapping().remove(column.get()));
    } else {
      throw new IllegalArgumentException("Column with name " + columnName + " not found.");
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
    return this.getSchema().getColumns().containsAll(row.getColumnValueMapping().keySet());
  }

  @Override
  public String toString() {
    return "Table: " + name + "\n" + schema.toString();
  }

  public String printTable() {
    StringBuilder sb = new StringBuilder();
    sb.append("Table: ").append(name).append("\n");
    sb.append("Schema: \n").append(this.schema.toString());
    sb.append("Values: \n");
    for (Column<?> column : this.schema.getColumns()) {
      sb.append(column.getName()).append(",");
    }
    sb.append("\n");
    for (Row row : entities) {
      sb.append(row.toRecord()).append("\n");
    }
    return sb.toString();
  }
}
