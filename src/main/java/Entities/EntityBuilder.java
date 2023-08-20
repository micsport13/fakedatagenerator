package Entities;

import Data.Column;
import Data.Constraint;
import Data.DataType;

import java.util.Set;

public interface EntityBuilder<T extends Entity> {
        EntityBuilder<T> addExistingColumn(Column column);
        EntityBuilder<T> addColumn(DataType dataType, String name, Set<Constraint> constraints);
        EntityBuilder<T> setColumnValue(Column column, Object value);
        T build();
    }

