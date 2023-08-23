package Data;

public class ColumnConstraint {
    private final Constraints constraint;
    ColumnConstraint() {
        this.constraint = null;
    }
    ColumnConstraint(Constraints constraint) {
        this.constraint = constraint;
    }
    public enum Constraints {
        NOT_NULL,
        CHECK
    }
}
