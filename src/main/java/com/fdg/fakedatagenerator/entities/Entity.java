package com.fdg.fakedatagenerator.entities;


import com.fdg.fakedatagenerator.column.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.*;

/**
 * The type Entity.
 */

@Getter
public class Entity {

    private final @NotNull EntityMap columnValueMapping;

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
     * Sets column value.
     *
     * @param columnName the column name
     * @param columnValue the column value
     */
    @SuppressWarnings("unchecked")
    public <T> void setColumnValue(String columnName, T columnValue) {
        Optional<Column<?>> column = getColumnByName(columnName);
        if (column.isPresent()) {
            if (columnValue == null || column.get().getDataType().isInstance(columnValue)) {
                this.columnValueMapping.add(column.get(), columnValue);
            } else {
                throw new IllegalArgumentException("Value is not compatible with the column's data type.");
            }
        } else {
            throw new IllegalArgumentException("Column does not exist.");
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
            string.append(value).append(",");
        }
        string.deleteCharAt(string.length() - 1);
        return string.toString();
    }

    /**
     * Gets column by name.
     *
     * @param columnName the column name
     *
     * @return the column by name
     */
    public Optional<Column<?>> getColumnByName(String columnName) {
        for (Column<?> column : this.columnValueMapping.getMap().keySet()) {
            if (Objects.equals(columnName, column.getName())) {
                return Optional.of(column);
            }
        }
        return Optional.empty();
    }

    public <T> Optional<T> getValue(String columnName) {
        Optional<Column<?>> optionalColumn = getColumnByName(columnName);
        return optionalColumn.map(column -> {
            @SuppressWarnings("unchecked") Column<T> typedColumn = (Column<T>) column;
            return this.getColumnValueMapping().get(typedColumn);
        });
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Map.Entry<Column<?>, ?> entry : columnValueMapping.getMap().entrySet()) {
            Column<?> column = entry.getKey();
            Object value = entry.getValue();
            string.append(column.toString()).append("\nValue: ").append(value).append("\n====================\n");
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
         *
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
         * @param value the value
         *
         * @return the builder
         */
        public <T> Builder withColumnValue(String columnName, T value) throws ClassCastException {
            if (value == null) {
                throw new IllegalArgumentException("Value cannot be null if calling this method");
            }
            for (Column<?> column : this.columnValueMapping.getMap().keySet()) {
                if (column.getName().equals(columnName) && column.getDataType().isInstance(value)) {
                    @SuppressWarnings("unchecked")
                    // Since the column type cannot be handled at compile time, this unchecked cast must be here.
                    Column<T> correctColumn = (Column<T>) column;
                    this.columnValueMapping.add(correctColumn, value);
                    return this;
                }
                if (column.getName().equals(columnName) && !column.getDataType().isInstance(value)) {
                    throw new IllegalArgumentException("Value does not match the column type of " + column.getDataType()
                                                                                                          .getSimpleName() + " of the column: " + columnName);
                }
            }
            return this;
        }

        private boolean existsColumn(String columnName) {
            for (Column<?> column : this.columnValueMapping.getMap().keySet()) {
                if (column.getName().equals(columnName)) {
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
