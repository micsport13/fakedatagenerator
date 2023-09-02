package Data.Exceptions;

public class PrimaryKeyConstraintException extends RuntimeException {
    public PrimaryKeyConstraintException(String message) {
        super(message);
    }
}
