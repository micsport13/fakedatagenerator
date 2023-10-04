package com.fakedatagenerator.schema;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.validators.TableValidators.TableValidator;
import com.fakedatagenerator.validators.Validator;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Schema implements Validator {
    private final Map<Column<?>, Set<TableValidator>> tableConstraints = new LinkedHashMap<>();

    public Schema(Column<?>... columns){
        for (Column<?> column :columns) {
            this.tableConstraints.put(column, new HashSet<>());
        }
    }

    public void addColumn(Column<?> column, TableValidator... tableValidators) { // TODO: Validate constraints if existing
        if (this.tableConstraints.get(column) != null){
            this.tableConstraints.get(column)
                    .addAll(Set.of(tableValidators));
        } else {
            this.tableConstraints.put(column, new HashSet<>(Set.of(tableValidators)));
        }
    }

    public Column<?> getColumn(String columnName) {
        for (Column<?> column:this.getColumns()) {
            if (column.getName().equals(columnName))
                    return column;
        }
        throw new IllegalArgumentException("Column with name " + columnName + " not found.");
    }

    public Map<Column<?>, Set<TableValidator>> getTableConstraints() {
        return this.tableConstraints;
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
                    .append(entry.getKey()
                                    .getName())
                    .append("\n")
                    .append("\tData type: ")
                    .append(entry.getKey()
                                    .getDataType().getSimpleName())
                    .append("\n")
                    .append("\tConstraints: ")
                    .append(entry.getValue()
                                    .toString())
                    .append("\n").append("--------------------\n");
        }
        return sb.toString();
    }
}
