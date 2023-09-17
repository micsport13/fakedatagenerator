package Data.Validators.TableValidators;

import Data.Exceptions.UniqueConstraintException;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Unique constraint.
 */
public class UniqueValidator implements TableValidator {
    private final Set<Object> uniqueValues; // TODO: Figure out how to check Uniqueness and Primary Keys

    /**
     * Instantiates a new Unique constraint.
     */
    public UniqueValidator() {
        this.uniqueValues = new HashSet<>();
    }
    private void addValue(Object value) {
        this.uniqueValues.add(value);
    }
    @Override
    public boolean validate(Object value) {
        if (this.uniqueValues.contains(value)){
            System.out.println("Value provided: " + value);
            throw new UniqueConstraintException("Value already exists in the unique constraint");
        };
        this.addValue(value);
        return true;
    }
    @Override
    public String toString() {
        return "Unique";
    }
}
