package Data;

public class TableConstraint {
    private final Constraints constraint;

    TableConstraint() {
        this.constraint = null;
    }
    TableConstraint(Constraints constraint) {
        this.constraint = constraint;
    }
    public enum Constraints {
        PRIMARY_KEY,
        FOREIGN_KEY,
        UNIQUE,
        CHECK
    }
}
