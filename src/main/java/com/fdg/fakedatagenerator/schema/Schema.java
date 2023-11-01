package com.fdg.fakedatagenerator.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.table.TableConstraint;
import com.fdg.fakedatagenerator.serializers.schema.SchemaDeserializer;
import com.fdg.fakedatagenerator.serializers.schema.SchemaSerializer;
import com.fdg.fakedatagenerator.constraints.Constraint;
import lombok.Getter;

import java.util.*;

@Getter
@JsonSerialize(using = SchemaSerializer.class)
@JsonDeserialize(using = SchemaDeserializer.class)
public class Schema implements Constraint {
    @JsonProperty("table_constraints")
    private final Map<Column<?>, Set<TableConstraint>> tableConstraints = new LinkedHashMap<>();

    public Schema(Column<?>... columns) {
        for (Column<?> column : columns) {
            this.tableConstraints.put(column, new HashSet<>());
        }
    }

    public void addColumn(Column<?> column, TableConstraint... tableConstraints) { // TODO: Validate constraints if existing
        if (this.tableConstraints.get(column) != null) {
            this.tableConstraints.get(column).addAll(Set.of(tableConstraints));
        } else {
            this.tableConstraints.put(column, new HashSet<>(Set.of(tableConstraints)));
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

    @Override
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
            sb.append("\tName: ")
              .append(entry.getKey().getName())
              .append("\n")
              .append("\tData type: ")
              .append(entry.getKey().getDataType())
              .append("\n")
              .append("\tConstraints: ")
              .append(entry.getValue().toString())
              .append("\n")
              .append("--------------------\n");
        }
        return sb.toString();
    }
}
