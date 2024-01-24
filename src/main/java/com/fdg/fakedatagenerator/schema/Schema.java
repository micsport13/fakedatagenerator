package com.fdg.fakedatagenerator.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.Constraint;
import com.fdg.fakedatagenerator.exceptions.ColumnNotFoundException;
import com.fdg.fakedatagenerator.row.Row;
import com.fdg.fakedatagenerator.schema.schema.SchemaDeserializer;
import com.fdg.fakedatagenerator.schema.schema.SchemaSerializer;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
@JsonSerialize(using = SchemaSerializer.class)
@JsonDeserialize(using = SchemaDeserializer.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Schema {
  @JsonProperty("table_constraints")
  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY)
  private final Map<Constraint, Set<Column<?>>> constraints;

  @Getter private final Set<Column<?>> columns;

  public Schema(Column<?>... columns) {
    this.columns = new LinkedHashSet<>(Arrays.asList(columns));
    this.constraints = new HashMap<>();
  }

  public void addColumn(Column<?> column) {
    this.columns.add(column);
  }

  public void addConstraint(Constraint constraint, Column<?>... columns) {
    this.constraints.put(constraint, new HashSet<>(Arrays.asList(columns)));
  }

  public Column<?> getColumn(
      String columnName) { // TODO: Should this be an optional or should it throw an error instead?
    for (Column<?> column : this.getColumns()) {
      if (column.getName().equals(columnName)) return column;
    }
    throw new ColumnNotFoundException("Column with name of " + columnName + " does not exist.");
  }

  public void validate(Row value) {
    for (Map.Entry<Constraint, Set<Column<?>>> entry : this.constraints.entrySet()) {
      Object validation =
          entry.getValue().stream()
              .map(value::getColumnValue)
              .map(Object::toString)
              .collect(Collectors.joining());
      entry.getKey().validate(validation);
    }
  }

  @Override
  public int hashCode() {
    return this.columns.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Schema schema)) {
      return false;
    }
    return this.columns.equals(schema.getColumns())
        && this.constraints.equals(schema.getConstraints());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Schema: \n");
    for (Column<?> column : this.columns) {
      sb.append(column.toString()).append("\n");
    }
    sb.append("Constraints: \n");
    for (Map.Entry<Constraint, Set<Column<?>>> entry : this.constraints.entrySet()) {
      sb.append(entry.getKey().toString())
          .append(": ")
          .append(entry.getValue().toString())
          .append("\n");
    }
    return sb.toString();
  }
}
