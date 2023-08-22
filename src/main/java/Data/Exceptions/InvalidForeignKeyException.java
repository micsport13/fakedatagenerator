package Data.Exceptions;

public class InvalidForeignKeyException extends RuntimeException {
    public InvalidForeignKeyException(String message) {
        super(message);
    }
}
