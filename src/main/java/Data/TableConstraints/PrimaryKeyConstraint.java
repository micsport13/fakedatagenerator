package Data.TableConstraints;

public class PrimaryKeyConstraint implements TableConstraint {
    @Override
    public boolean isValid(Object value) {
        return false;
    }
}
