package Data.Column;

public class NotNullConstraint implements ColumnConstraint{
    @Override
    public boolean isValid(Object value) {
        return value != null;
    }
}
