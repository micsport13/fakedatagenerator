package Entities;

import Data.Column;
import Data.Constraint;
import Data.Exceptions.InvalidForeignKeyException;
import Data.Exceptions.InvalidPrimaryKeyException;
import Data.Exceptions.InvalidUniquessException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LicenseFactory implements EntityFactory<License>{
    private final Set<Object> primaryKeys = new HashSet<>();
    private final Map<Column,Set<Object>> foreignKeys = new HashMap<>();
    private final Set<Object> uniqueKeys = new HashSet<>();
    @Override
    public License createEntity(Set<Column> values) {
            License license = new License(values);
            if (this.isValidEntity(license)) {
                this.updateKeySets(license);
            }
            return license;
        }


    @Override
    public boolean isValidEntity(License license) {
        for (Map.Entry<Column, Object> entry : license.getColumnValueMapping().entrySet()){
            if (entry.getKey().getConstraint() != null) {
                if (entry.getKey().getConstraint().contains(Constraint.PRIMARY_KEY) && this.primaryKeys.contains(entry.getValue())) {
                    throw new InvalidPrimaryKeyException("Contains a value that violates primary key constraint");
                }
                if (entry.getKey().getConstraint().contains(Constraint.UNIQUE) && this.uniqueKeys.contains(entry.getValue())) {
                    throw new InvalidUniquessException("Contains a value that violates unique key constraint");
                }
                if (entry.getKey().getConstraint().contains(Constraint.FOREIGN_KEY) && !this.foreignKeys.get(entry.getKey()).contains(entry.getValue())) {
                    throw new InvalidForeignKeyException("Contains a value that violates foreign key constraint");
                }
            }
        }
        return true;
    }
    private void updateKeySets(License license) {
        for (Column column : license.getColumns()) {
            if (column.getConstraint() != null) {
                Object columnValue = license.getColumnValueMapping().get(column);
                if (column.getConstraint().contains(Constraint.PRIMARY_KEY)) {
                    primaryKeys.add(columnValue);
                }
                if (column.getConstraint().contains(Constraint.UNIQUE)) {
                    uniqueKeys.add(columnValue);
                }
            }
        }
    }

    public Set<Object> getPrimaryKeys() {
        return primaryKeys;
    }

    public Map<Column, Set<Object>> getForeignKeys() {
        return foreignKeys;
    }

    public void addForeignKeyValue(Column column, Set<Object> object) {
        this.foreignKeys.put(column, object);
    }

    public Set<Object> getUniqueKeys() {
        return uniqueKeys;
    }
}
