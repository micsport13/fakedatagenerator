package Data.TableConstraints;

import Data.Exceptions.PrimaryKeyConstraintException;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Primary key constraint.
 */
public class PrimaryKeyConstraint implements TableConstraint {
    private final Set<Object> primaryKeyValues = new HashSet<>();
    private void addValue(Object value) {
        primaryKeyValues.add(value);
    }
    @Override
    public boolean isValid(Object value) {
        if (value == null) {
            throw new PrimaryKeyConstraintException("Primary key cannot be null");
        } else if (primaryKeyValues.contains(value)) {
            System.out.println("Value provided: " + value);
            throw new PrimaryKeyConstraintException("Primary key must be unique");
        }
        this.addValue(value);
        return true;
    }
}
