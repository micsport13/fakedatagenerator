package Data.Column;

import Data.Exceptions.NotNullConstraintException;

public class NotNullConstraint implements ColumnConstraint{
    @Override
    public boolean isValid(Object value) {
        if (value == null) {
            throw new NotNullConstraintException("Value cannot be null");
        }
        return true;
    }
}
