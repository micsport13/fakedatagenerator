package Data.Exceptions;

public class CheckConstraintException extends RuntimeException {
    public CheckConstraintException(String message) {
        super(message);
    }
}
