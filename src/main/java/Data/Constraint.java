package Data;

public interface Constraint {
    default boolean isValid(Object value) {return true;};
}
