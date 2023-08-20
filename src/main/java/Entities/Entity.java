package Entities;


import Data.Column;
import Data.Constraint;
import Data.Exceptions.MismatchedDataTypeException;
import Data.Exceptions.NotNullConstraintException;

import java.util.*;

public abstract class Entity {
    protected Set<Column> columns = new LinkedHashSet<>();
    protected Map<Column, Object> columnValueMapping = new LinkedHashMap<>();

    protected Entity(Set<Column> columns, Map<String, Object> values) {
        this.setColumns(columns);
        this.setValues(values);
    }
    public Set<Column> getColumns() {
        return columns;
    }
    public Map<Column, Object> getColumnValueMapping() {
        return columnValueMapping;
    }
    protected abstract void setColumns(Set<Column> columnList);

    protected void setValues(Map<String, Object> values) {
        for (Column column : this.columns) {
            if (isValid(column, values.get(column.name))) {
                this.columnValueMapping.put(column, values.get(column.name));
            } else {
                System.out.println(column);
                System.out.println("Value provided: " + values.get(column.name));
                throw new IllegalArgumentException("Invalid value for column " + column.name);
            }
        }
    }

    protected static boolean isValid(Column column, Object value) {
        if (value == null) {
            boolean b = !(column.getConstraint().contains(Constraint.NOT_NULL) || column.getConstraint().contains(Constraint.PRIMARY_KEY));
            throw new NotNullConstraintException("Value cannot be null for column: " + column.name);
        }
        if (value.getClass() != column.getDataType().getAssociatedClass()) {
            throw new MismatchedDataTypeException("Value is of type " + value.getClass().getSimpleName() + " and column is of type " + column.getDataType().getAssociatedClass().getSimpleName());
        }
        return true;
    }


    public String toRecord() {
        StringBuilder string = new StringBuilder();
        for (Column column : this.columns) {
            Object value = this.columnValueMapping.get(column);
            string.append(value).append(",");
        }
        string.deleteCharAt(string.length() - 1);
        return string.toString();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Map.Entry<Column, Object> entry : columnValueMapping.entrySet()) {
            Column column = entry.getKey();
            Object value = entry.getValue();
            string.append(column.toString()).append("\nValue: ").append(value).append("\n====================\n");
        }
        return string.toString();
    }
}
