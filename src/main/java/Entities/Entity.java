package Entities;


import Data.Column.Column;

import java.util.*;

public class Entity {
    private final Map<Column, Object> columnValueMapping;

    private Entity(Builder builder) {
        this.columnValueMapping = builder.columnValueMapping;
    }

    public static class Builder {
        private final Map<Column, Object> columnValueMapping = new LinkedHashMap<>();
        public Builder(Column... columnList) {
            for (Column column : columnList) {
                this.columnValueMapping.put(column, null);
            }
        }
        public Builder addColumn(Column column) {
           this.columnValueMapping.put(column, null);
            return this;
        }

        public Builder withColumnValue(String columnName, Object value) {
            if (!this.existsColumn(columnName)) {
                throw new IllegalArgumentException(columnName + " does not exist for the " + this.getClass().getSimpleName() + " entity.");
            }
            if (Objects.requireNonNull(getColumnByName(columnName)).isValid(value)) {
                this.columnValueMapping.put(getColumnByName(columnName), value);
            }
            return this;
        }
        private boolean existsColumn(String columnName) {
            for (Column column : this.columnValueMapping.keySet()) {
                if (column.getName().equals(columnName)) {
                    return true;
                }
            }
            return false;
        }
        private Column getColumnByName(String columnName) {
            for (Column column : this.columnValueMapping.keySet()) {
                if (column.getName().equals(columnName)) {
                    return column;
                }
            }
            return null;
        }

        public Entity build() {
            return new Entity(this);
        };
    }
    public Set<Column> getColumns() {
        return new HashSet<>(this.columnValueMapping.keySet());
    }


    public Map<Column, Object> getColumnValueMapping() {
        return new HashMap<>(this.columnValueMapping);
    }
    public void setColumnValue(String columnName, Object columnValue) {
        for (Column column : this.columnValueMapping.keySet()) {
            if (Objects.equals(columnName, column.getName())) {
                this.columnValueMapping.put(column, columnValue);
            }
        }
    }


    public String toRecord() {
        StringBuilder string = new StringBuilder();
        for (Column column : this.columnValueMapping.keySet()) {
            Object value = this.columnValueMapping.get(column);
            string.append(value)
                    .append(",");
        }
        string.deleteCharAt(string.length() - 1);
        return string.toString();
    }

    public Column getColumnByName(String columnName) {
        for (Column column : this.columnValueMapping.keySet()) {
            if (Objects.equals(columnName, column.getName())) {
                return column;
            }
        }
        throw new IllegalArgumentException("Table column with name " + columnName + " does not exist.");
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
