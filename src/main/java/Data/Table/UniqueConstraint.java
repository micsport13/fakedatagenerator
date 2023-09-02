package Data.Table;

import Data.Column.Column;
import Data.Exceptions.UniqueConstraintException;

import java.util.HashSet;
import java.util.Set;

public class UniqueConstraint implements TableConstraint {
    private final Set<Object> uniqueValues;

    public UniqueConstraint() {
        this.uniqueValues = new HashSet<>();
    }
    public void addValue(Object object) {
        if (this.isValid(object)) {
            this.uniqueValues.add(object);
        }
    }
    public void addValues(Object... objects) {
        for (Object object : objects) {
            if (this.isValid(object)) {
                this.uniqueValues.add(object);
            }
        }
    }
    @Override
    public boolean isValid(Object value) {
        if (this.uniqueValues.contains(value)){
            throw new UniqueConstraintException("Value already exists in the unique constraint");
        };
        return true;
    }
}
