package Data.TableConstraints;

import Data.Exceptions.CheckConstraintException;

/**
 * The type Table check constraint.
 */
public class TableCheckConstraint implements TableConstraint {
    // TODO: Figure out how to link other columns to this constraint

    @Override
    public boolean isValid(Object value) {
        throw new UnsupportedOperationException("Check constraint validation not yet implemented");
    }
}
