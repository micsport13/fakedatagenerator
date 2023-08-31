package Data.Exceptions;

import Data.Column.CheckConstraint;

public class CheckConstraintException extends RuntimeException {
    public CheckConstraintException(String message) {
        super(message);
    }
}
