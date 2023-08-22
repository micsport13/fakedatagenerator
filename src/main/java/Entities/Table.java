package Entities;

import Data.Column;
import Data.Constraint;
import Data.Exceptions.InvalidForeignKeyException;
import Data.Exceptions.InvalidPrimaryKeyException;
import Data.Exceptions.InvalidUniquessException;

import java.util.*;
import java.util.function.Consumer;

public class Table<T extends Entity> {
    private final Map<Column, Set<Object>> primaryKeys = new HashMap<>();
    private final Map<Column,Set<Object>> foreignKeys = new HashMap<>();
    private final Map<Column, Set<Object>> uniqueKeys = new HashMap<>();
    private final List<T> entities = new ArrayList<>();

    public List<T> getEntities() {
        return entities;
    }

    public void add(T entity) {
        if (isValidEntity(entity)) {
            entities.add(entity);
            updateKeySets(entity);
        }
    }

    public boolean isValidEntity(T entity) {
        for (Map.Entry<Column, Object> entry : entity.getColumnValueMapping().entrySet()){
            if (entry.getKey().getConstraints() != null) {
                if (entry.getKey().getConstraints().contains(Constraint.PRIMARY_KEY) && this.primaryKeys.containsValue(Set.of(entry.getValue()))) {
                    throw new InvalidPrimaryKeyException("Contains a value that violates primary key constraint");
                }
                if (entry.getKey().getConstraints().contains(Constraint.UNIQUE) && this.uniqueKeys.containsValue(Set.of(entry.getValue()))) {
                    throw new InvalidUniquessException(entity.getClass().getSimpleName() + " contains a value that violates unique key constraint of column: " + entry.getKey().name);
                }
                if (entry.getKey().getConstraints().contains(Constraint.FOREIGN_KEY) && !this.foreignKeys.get(entry.getKey()).contains(entry.getValue())) {
                    throw new InvalidForeignKeyException(entity.getClass().getSimpleName() + " contains a value that violates foreign key constraint of column: " + entry.getKey().name);
                }
            }
        }
        return true;
    }

    public void addForeignKeyValue(Column column, Set<Object> value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void updateKeySets(T entity) {
        Set<Column> columns = entity.getColumns();
        Map<Column, Object> columnValueMapping = entity.getColumnValueMapping();
        for (Column column : columns) {
            if (column.getConstraints() != null) {
                Object columnValue = columnValueMapping.get(column);
                if (column.getConstraints().contains(Constraint.PRIMARY_KEY)) {
                    this.primaryKeys.put(column, Set.of(columnValueMapping.get(column)));
                }
                if (column.getConstraints().contains(Constraint.UNIQUE)) {
                    this.uniqueKeys.put(column, Set.of(columnValue));
                }
            }
        }
    }


    public Map<Column, Set<Object>> getPrimaryKeys() {
        return primaryKeys;
    }

    public Map<Column, Set<Object>> getForeignKeys() {
        return foreignKeys;
    }

    public Map<Column, Set<Object>> getUniqueKeys() {
        return uniqueKeys;
    }

}
