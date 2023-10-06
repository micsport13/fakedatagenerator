package com.fdg.fakedatagenerator.entities;


import com.fdg.fakedatagenerator.column.Column;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The type Entity.
 */
public class Entity {
    private final EntityMap columnValueMapping;

    private Entity(Builder builder) {
        this.columnValueMapping = builder.columnValueMapping;
    }

    /**
     * Gets columns.
     *
     * @return the columns
     */
    public Set<Column<?>> getColumns() {
        return new LinkedHashSet<>(this.columnValueMapping.getMap().keySet());
    }

    /**
     * Gets column value mapping.
     *
     * @return the column value mapping
     */
    public EntityMap getColumnValueMapping() {
        return this.columnValueMapping;
    }

    /**
     * Sets column value.
     *
     * @param columnName  the column name
     * @param columnValue the column value
     */
    @SuppressWarnings("unchecked")
    public <T> void setColumnValue(String columnName, T columnValue) {
        Column<T> column = (Column<T>) getColumnByName(columnName);

        if (columnValue == null || column.getDataType().isInstance(columnValue)) {
            this.columnValueMapping.add(column, columnValue);
        } else {
            throw new IllegalArgumentException("Value is not compatible with the column's data type.");
        }
    }


    /**
     * To record string.
     *
     * @return the string
     */
    public String toRecord() {
        StringBuilder string = new StringBuilder();
        for (Column<?> column : this.columnValueMapping.getMap().keySet()) {
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
    public Column<?> getColumnByName(String columnName) {
        for (Column<?> column : this.columnValueMapping.getMap().keySet()) {
            if (Objects.equals(columnName, column.getName())) {
                return column;
            }
        }
        throw new IllegalArgumentException("Table column with name " + columnName + " does not exist.");
    }

    public <T> T getValue(String columnName) {
        @SuppressWarnings("unchecked")
        Column<T> column = (Column<T>) getColumnByName(columnName);
        return this.getColumnValueMapping().get(column);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Map.Entry<Column<?>, ?> entry : columnValueMapping.getMap().entrySet()) {
            Column<?> column = entry.getKey();
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
        private final EntityMap columnValueMapping = new EntityMap();

        /**
         * Instantiates a new SchemaBuilder.
         *
         * @param columnList the column list
         */
        public Builder(Column<?>... columnList) {
            for (Column<?> column : columnList) {
                this.columnValueMapping.add(Objects.requireNonNull(column), null);
            }
        }

        /**
         * Add column builder.
         *
         * @param column the column
         * @return the builder
         */
        public Builder addColumn(Column<?> column) {
            this.columnValueMapping.add(column, null);
            return this;
        }

        /**
         * With column value builder.
         *
         * @param columnName the column name
         * @param value      the value
         * @return the builder
         */
        public <T> Builder withColumnValue(String columnName, T value) throws ClassCastException {
            if (value == null) {
                throw new IllegalArgumentException("Value cannot be null if calling this method");
            }
            for (Column<?> column : this.columnValueMapping.getMap().keySet()) {
                if (column.getName()
                        .equals(columnName) && column.getDataType().isInstance(value)) {
                    @SuppressWarnings("unchecked")
                            // Since the column type cannot be handled at compile time, this unchecked cast must be here.
                    Column<T> correctColumn = (Column<T>) column;
                    this.columnValueMapping.add(correctColumn, value);
                    return this;
                }
                if (column.getName()
                        .equals(columnName) && !column.getDataType().isInstance(value)) {
                    throw new IllegalArgumentException("Value does not match the column type of " + column.getDataType().getSimpleName() + " of the column: " + columnName);
                }
            }
            return this;
        }

        private boolean existsColumn(String columnName) {
            for (Column<?> column : this.columnValueMapping.getMap().keySet()) {
                if (column.getName()
                        .equals(columnName)) {
                    return true;
                }
            }
            return false;
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
