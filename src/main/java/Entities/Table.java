package Entities;

import Data.Column;
import Data.Exceptions.InvalidPrimaryKeyException;
import Data.Exceptions.InvalidUniquessException;
import Data.TableConstraint;

import java.util.*;

public class Table<T extends Entity> {
    private final Map<Column, Set<Object>> tableValues = new LinkedHashMap<>();
    private final Map<Column, Set<TableConstraint>> tableConstraints = new HashMap<>();
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
                if (entry.getKey().getConstraints().contains(Constraint.PRIMARY_KEY) && this.tableValues.containsValue(Set.of(entry.getValue()))) {
                    throw new InvalidPrimaryKeyException("Contains a value that violates primary key constraint");
                }
                if (entry.getKey().getConstraints().contains(Constraint.UNIQUE) && this.tableValues.containsValue(Set.of(entry.getValue()))) {
                    throw new InvalidUniquessException(entity.getClass().getSimpleName() + " contains a value that violates unique key constraint of column: " + entry.getKey().name);
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
            if (columnValueMapping.get(column) != null) {
                this.tableValues.put(column, Set.of(columnValueMapping.get(column)));
            }
        }
    }
    public Map<Column, Set<Object>> getTableValues() {
        return this.tableValues;
    }

}
