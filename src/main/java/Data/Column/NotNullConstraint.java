package Data.Column;

import Data.Exceptions.NotNullConstraintException;

/**
 * The type Not null constraint.
 */
public class NotNullConstraint implements ColumnConstraint{
    @Override
    public boolean isValid(Object value) {
        if (value == null) {
            throw new NotNullConstraintException("Value cannot be null");
        }
        return true;
    }
}
