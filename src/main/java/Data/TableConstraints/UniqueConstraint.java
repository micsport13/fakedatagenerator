package Data.TableConstraints;

import Data.Exceptions.UniqueConstraintException;

import java.util.HashSet;
import java.util.Set;

public class UniqueConstraint implements TableConstraint {
    private final Set<Object> uniqueValues; // TODO: Figure out how to check Uniqueness and Primary Keys

    public UniqueConstraint() {
        this.uniqueValues = new HashSet<>();
    }
    private void addValue(Object value) {
        this.uniqueValues.add(value);
    }
    @Override
    public boolean isValid(Object value) {
        if (this.uniqueValues.contains(value)){
            throw new UniqueConstraintException("Value already exists in the unique constraint");
        };
        this.addValue(value);
        return true;
    }
}
