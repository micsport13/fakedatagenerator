package Entities;

import Data.Column;
import Data.Constraint;
import Data.Exceptions.InvalidForeignKeyException;
import Data.Exceptions.InvalidPrimaryKeyException;
import Data.Exceptions.InvalidUniquessException;

import java.util.*;

public class Table<T extends Entity> {
    private final Map<Column, Set<Object>> primaryKeys = new HashMap<>();
    private final Map<Column,Set<Object>> foreignKeys = new HashMap<>();
    private final Map<Column, Set<Object>> uniqueKeys = new HashMap<>();
    private final List<T> entities = new ArrayList<>();

    public boolean isValidEntity(Member entity) {
        for (Map.Entry<Column, Object> entry : entity.getColumnValueMapping().entrySet()){
            if (entry.getKey().getConstraints() != null) {
                if (entry.getKey().getConstraints().contains(Constraint.PRIMARY_KEY) && this.primaryKeys.contains(entry.getValue())) {
                    throw new InvalidPrimaryKeyException("Contains a value that violates primary key constraint");
                }
                if (entry.getKey().getConstraints().contains(Constraint.UNIQUE) && this.uniqueKeys.contains(entry.getValue())) {
                    throw new InvalidUniquessException("Contains a value that violates unique key constraint");
                }
                if (entry.getKey().getConstraints().contains(Constraint.FOREIGN_KEY) && !this.foreignKeys.get(entry.getKey()).contains(entry.getValue())) {
                    throw new InvalidForeignKeyException("Contains a value that violates foreign key constraint");
                }
            }
        }
        return true;
    }

    public void addForeignKeyValue(Column column, Set<Object> value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void updateKeySets(Member member) {
        for (Column column : member.getColumns()) {
            if (column.getConstraints() != null) {
                Object columnValue = member.getColumnValueMapping().get(column);
                if (column.getConstraints().contains(Constraint.PRIMARY_KEY)) {
                    primaryKeys.add(columnValue);
                }
                if (column.getConstraints().contains(Constraint.UNIQUE)) {
                    uniqueKeys.add(columnValue);
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
