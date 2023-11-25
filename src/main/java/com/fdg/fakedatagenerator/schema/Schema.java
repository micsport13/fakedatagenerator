package com.fdg.fakedatagenerator.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.table.TableConstraint;
import com.fdg.fakedatagenerator.serializers.schema.SchemaDeserializer;
import com.fdg.fakedatagenerator.serializers.schema.SchemaSerializer;
import lombok.Getter;

import java.util.*;

@Getter
@JsonSerialize(using = SchemaSerializer.class)
@JsonDeserialize(using = SchemaDeserializer.class)
public class Schema {
  @JsonProperty("table_constraints")
  private final Map<Column<?>, Set<TableConstraint>> tableConstraints = new LinkedHashMap<>();

  public Schema(Column<?>... columns) {
    for (Column<?> column : columns) {
      this.tableConstraints.put(column, new HashSet<>());
    }
  }

  public Schema(Map<Column<?>, Set<TableConstraint>> schemaMap) {
    this.tableConstraints.putAll(schemaMap);
  }

  public void addColumn(
      Column<?> column,
      TableConstraint... tableConstraints) { // TODO: Validate constraints if existing
    if (this.tableConstraints.get(column) != null) {
      this.tableConstraints.get(column).addAll(Set.of(tableConstraints));
    } else {
      this.tableConstraints.put(column, new HashSet<>(Set.of(tableConstraints)));
    }
  }

  public void addConstraint(Column<?> column, TableConstraint tableConstraint) {
    for (var schemaColumn : this.tableConstraints.keySet()) {
      if (schemaColumn.equals(column)) {
        this.tableConstraints.get(schemaColumn).add(tableConstraint);
      }
    }
  }

  public Optional<Column<?>> getColumn(String columnName) {
    for (Column<?> column : this.getColumns()) {
      if (column.getName().equals(columnName)) return Optional.of(column);
    }
    return Optional.empty();
  }

  public Set<Column<?>> getColumns() {
    return this.tableConstraints.keySet();
  }

  public void validate(Object value) {
    for (Map.Entry<Column<?>, Set<TableConstraint>> entry : this.tableConstraints.entrySet()) {
      for (TableConstraint tableConstraint : entry.getValue()) {
        tableConstraint.validate(value);
      }
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Schema: \n");
    for (Map.Entry<Column<?>, Set<TableConstraint>> entry : this.tableConstraints.entrySet()) {
      sb.append(entry.getKey().toString()).append("\n")
          .append("Table Constraints: ")
          .append(entry.getValue().toString())
          .append("\n")
          .append("--------------------\n");
    }
    return sb.toString();
  }

  @Override
  public int hashCode() {
    return this.tableConstraints.hashCode();
  }
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Schema schema)) {
      return false;
    }
    return this.tableConstraints.equals(schema.tableConstraints);
  }
}
