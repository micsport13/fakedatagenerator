package Entities;

import Data.Column;

import java.util.Set;

public interface EntityFactory<T extends Entity> {
    T createEntity(Set<Column> values);
    public boolean isValidEntity(T entity);
    public void addForeignKeyValue(Column column, Object value);
}
