package Data.TableConstraints;

public class ForeignKeyConstraint implements TableConstraint{

    @Override
    public boolean isValid(Object value) {
        return true;
    }
}
