package Entities;


import Data.Column.Column;

import java.util.*;

public abstract class Entity {
    protected Set<Column> columns = new LinkedHashSet<>();
    protected Map<Column, Object> columnValueMapping = new LinkedHashMap<>();

    protected Entity(EntityBuilder<?,?> builder) {
        this.columns = builder.columnList;
        this.columnValueMapping = builder.columnValueMapping;
    }

    protected abstract static class EntityBuilder<T extends EntityBuilder<T,S>, S extends Entity> {
        protected final Set<Column> columnList = new LinkedHashSet<>();
        protected Map<Column, Object> columnValueMapping = new LinkedHashMap<>();

        protected EntityBuilder() {
        }
        protected EntityBuilder(Set<Column> columnList) {
            this.columnList.addAll(columnList);
        }


        public T addColumn(Column column) {
            self().columnList.add(column);
            return self();
        }

        public T withColumnValue(String columnName, Object value) {
            if (!isValidColumn(columnName)) {
                throw new IllegalArgumentException(columnName + " does not exist for the " + self().getClass().getSimpleName() + " entity.");
            }
            if (Objects.requireNonNull(getColumnByName(columnName)).isValid(value)) {
                self().columnValueMapping.put(getColumnByName(columnName), value);
            }
            return self();
        }
        private boolean isValidColumn(String columnName) {
            for (Column column : self().columnList) {
                if (column.getName().equals(columnName)) {
                    return true;
                }
            }
            return false;
        }
        private Column getColumnByName(String columnName) {
            for (Column column : self().columnList) {
                if (column.getName().equals(columnName)) {
                    return column;
                }
            }
            return null;
        }

        abstract S build();
        protected abstract T self();
    }
    public Set<Column> getColumns() {
        return columns;
    }


    public Map<Column, Object> getColumnValueMapping() {
        return columnValueMapping;
    }


    public String toRecord() {
        StringBuilder string = new StringBuilder();
        for (Column column : this.columns) {
            Object value = this.columnValueMapping.get(column);
            string.append(value)
                    .append(",");
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
