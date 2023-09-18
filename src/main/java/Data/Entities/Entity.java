package Data.Entities;


import Data.Column.Column;

import java.util.*;

/**
 * The type Entity.
 */
public class Entity {
    private final Map<Column, Object> columnValueMapping;

    private Entity(Builder builder) {
        this.columnValueMapping = builder.columnValueMapping;
    }

    /**
     * Gets columns.
     *
     * @return the columns
     */
    public Set<Column> getColumns() {
        return new HashSet<>(this.columnValueMapping.keySet());
    }

    /**
     * Gets column value mapping.
     *
     * @return the column value mapping
     */
    public Map<Column, Object> getColumnValueMapping() {
        return new HashMap<>(this.columnValueMapping);
    }

    /**
     * Sets column value.
     *
     * @param columnName  the column name
     * @param columnValue the column value
     */
    public void setColumnValue(String columnName, Object columnValue) {
        for (Column column : this.columnValueMapping.keySet()) {
            if (Objects.equals(columnName, column.getName()) && column.isValid(columnValue)) {
                this.columnValueMapping.put(column, columnValue);
            }
        }
    }

    /**
     * To record string.
     *
     * @return the string
     */
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

    /**
     * Gets column by name.
     *
     * @param columnName the column name
     * @return the column by name
     */
    public Column getColumnByName(String columnName) {
        for (Column column : this.columnValueMapping.keySet()) {
            if (Objects.equals(columnName, column.getName())) {
                return column;
            }
        }
        throw new IllegalArgumentException("Table column with name " + columnName + " does not exist.");
    }

    public Object getValue(String columnName) {
        for (Column column : this.columnValueMapping.keySet()) {
            if (Objects.equals(columnName, column.getName())) {
                return this.columnValueMapping.get(column);
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
            string.append(column.toString())
                    .append("\nValue: ")
                    .append(value)
                    .append("\n====================\n");
        }
        return string.toString();
    }

    /**
     * The type SchemaBuilder.
     */
    public static class Builder {
        private final Map<Column, Object> columnValueMapping = new LinkedHashMap<>();

        /**
         * Instantiates a new SchemaBuilder.
         *
         * @param columnList the column list
         */
        public Builder(Column... columnList) {
            for (Column column : columnList) {
                this.columnValueMapping.put(Objects.requireNonNull(column), null);
            }
        }

        /**
         * Add column builder.
         *
         * @param column the column
         * @return the builder
         */
        public Builder addColumn(Column column) {
            this.columnValueMapping.put(column, null);
            return this;
        }

        /**
         * With column value builder.
         *
         * @param columnName the column name
         * @param value      the value
         * @return the builder
         */
        public Builder withColumnValue(String columnName, Object value) {
            if (!this.existsColumn(columnName)) {
                throw new IllegalArgumentException(columnName + " does not exist for the " + this.getClass()
                        .getSimpleName() + " entity.");
            }
            if (Objects.requireNonNull(getColumnByName(columnName))
                    .isValid(value)) {
                this.columnValueMapping.put(getColumnByName(columnName), value);
            }
            return this;
        }

        private boolean existsColumn(String columnName) {
            for (Column column : this.columnValueMapping.keySet()) {
                if (column.getName()
                        .equals(columnName)) {
                    return true;
                }
            }
            return false;
        }

        private Column getColumnByName(String columnName) {
            for (Column column : this.columnValueMapping.keySet()) {
                if (column.getName()
                        .equals(columnName)) {
                    return column;
                }
            }
            return null;
        }

        /**
         * Build entity.
         *
         * @return the entity
         */
        public Entity build() {
            return new Entity(this);
        }

    }
}
