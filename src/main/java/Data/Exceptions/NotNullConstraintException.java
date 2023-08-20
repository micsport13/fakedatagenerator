package Data.Exceptions;

public class NotNullConstraintException extends RuntimeException {
    public NotNullConstraintException(String message) {
        super(message);
    }
}
