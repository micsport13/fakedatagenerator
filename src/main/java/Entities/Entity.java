package Entities;


import Data.Column;
import Data.Constraint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Entity {
    protected final List<Column> columns = new ArrayList<>();
    protected Map<Column, Object> columnValueMapping = new HashMap<>();

    protected Entity(Map<String, Object> values) {
        this.setColumns();
        this.setValues(values);
    }
    protected static boolean isValid(Column column, Object value) {
        if (value == null) {
            boolean b = !(column.getConstraint().contains(Constraint.NOT_NULL)||column.getConstraint().contains(Constraint.PRIMARY_KEY));
            throw new RuntimeException("Value cannot be null for column: " + column.name);
        }
        if (value.getClass() != column.getDataType().getAssociatedClass()){
            throw new RuntimeException("Value is of type " + value.getClass().getSimpleName() + " and column is of type " + column.getDataType().getAssociatedClass().getSimpleName());
        };
        return true;
    }

    protected abstract void setColumns();

    public List<Column> getColumns() {
        return columns;
    }

    public Map<Column, Object> getColumnValueMapping() {
        return columnValueMapping;
    }
    protected void setValues(Map<String, Object> values) {
        for (Column column: this.columns) {
            if (isValid(column, values.get(column.name))) {
                this.columnValueMapping.put(column, values.get(column.name));
            } else {
                System.out.println(column);
                System.out.println("Value provided: " + values.get(column.name));
                throw new IllegalArgumentException("Invalid value for column " + column.name);
            }
        }
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
