package com.fdg.fakedatagenerator.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.serializers.schema.SchemaDeserializer;
import com.fdg.fakedatagenerator.serializers.schema.SchemaSerializer;
import com.fdg.fakedatagenerator.validators.TableValidators.TableValidator;
import com.fdg.fakedatagenerator.validators.Validator;
import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Getter
@JsonSerialize(using = SchemaSerializer.class)
@JsonDeserialize(using = SchemaDeserializer.class)
public class Schema implements Validator {
    @JsonProperty("table_constraints")
    private final Map<Column<?>, Set<TableValidator>> tableConstraints = new LinkedHashMap<>();

    public Schema(Column<?>... columns) {
        for (Column<?> column : columns) {
            this.tableConstraints.put(column, new HashSet<>());
        }
    }

    public void addColumn(Column<?> column, TableValidator... tableValidators) { // TODO: Validate constraints if existing
        if (this.tableConstraints.get(column) != null) {
            this.tableConstraints.get(column).addAll(Set.of(tableValidators));
        } else {
            this.tableConstraints.put(column, new HashSet<>(Set.of(tableValidators)));
        }
    }

    public Column<?> getColumn(String columnName) {
        for (Column<?> column : this.getColumns()) {
            if (column.getName().equals(columnName)) return column;
        }
        throw new IllegalArgumentException("Column with name " + columnName + " not found.");
    }

    public Set<Column<?>> getColumns() {
        return this.tableConstraints.keySet();
    }

    @Override
    public void validate(Object value) {
        for (Map.Entry<Column<?>, Set<TableValidator>> entry : this.tableConstraints.entrySet()) {
            for (TableValidator tableValidator : entry.getValue()) {
                tableValidator.validate(value);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Schema: \n");
        for (Map.Entry<Column<?>, Set<TableValidator>> entry : this.tableConstraints.entrySet()) {
            sb.append("\tName: ")
              .append(entry.getKey().getName())
              .append("\n")
              .append("\tData type: ")
              .append(entry.getKey().getDataType().getSimpleName())
              .append("\n")
              .append("\tConstraints: ")
              .append(entry.getValue().toString())
              .append("\n")
              .append("--------------------\n");
        }
        return sb.toString();
    }
}
