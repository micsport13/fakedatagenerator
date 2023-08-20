package Data.Exceptions;

public class InvalidPrimaryKeyException extends RuntimeException{
    public InvalidPrimaryKeyException(String message) {
        super(message);
    }
}
