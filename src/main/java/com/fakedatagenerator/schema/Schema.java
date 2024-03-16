package com.fakedatagenerator.schema;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.constraints.Constraint;
import com.fakedatagenerator.constraints.StateConstraint;
import com.fakedatagenerator.exceptions.ColumnNotFoundException;
import com.fakedatagenerator.generators.SequentialValueGenerator;
import com.fakedatagenerator.row.Row;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
@JsonSerialize(using = SchemaSerializer.class)
@JsonDeserialize(using = SchemaDeserializer.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Schema {
  @JsonProperty("table_constraints")
  private final Map<Constraint, Set<Column>> constraints;

  @Getter private final Set<Column> columns;

  public Schema(Column... columns) {
    this.columns = new LinkedHashSet<>(Arrays.asList(columns));
    this.constraints = new LinkedHashMap<>();
  }

  public void addColumn(Column column) {
    this.columns.add(column);
  }

  public void addConstraint(Constraint constraint, Column... columns) {
    this.constraints.put(constraint, new HashSet<>(Arrays.asList(columns)));
  }

  public Column getColumn(
      String columnName) { // TODO: Should this be an optional or should it throw an error instead?
    for (Column column : this.getColumns()) {
      if (column.getName().equals(columnName)) return column;
    }
    throw new ColumnNotFoundException("Column with name of " + columnName + " does not exist.");
  }

  public void validate(Row value) {
    for (Map.Entry<Constraint, Set<Column>> entry : this.constraints.entrySet()) {
      Object validation =
          entry.getValue().stream()
              .map(value::getColumnValue)
              .map(Object::toString) // Causes a validation exception, figure out how to check
              .collect(Collectors.joining());
      entry.getKey().validate(validation);
    }
  }

  public void resetSequences() {
    for (var constraint : this.constraints.keySet()) {
      if (StateConstraint.class.isAssignableFrom(constraint.getClass())) {
        ((StateConstraint) constraint).reset();
      }
    }
    for (var column : this.columns) {
      if (column.getValueGenerator() instanceof SequentialValueGenerator) {
        ((SequentialValueGenerator) column.getValueGenerator()).reset();
      }
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
    for (Column column : this.columns) {
      sb.append(column.toString()).append("\n");
    }
    sb.append("==================\n");
    sb.append("Constraints: \n");
    for (Map.Entry<Constraint, Set<Column>> entry : this.constraints.entrySet()) {
      sb.append(entry.getKey().toString())
          .append(": \n")
          .append(entry.getValue().toString().replace("\n", ", "))
          .append("\n");
    }
    return sb.toString();
  }
}
