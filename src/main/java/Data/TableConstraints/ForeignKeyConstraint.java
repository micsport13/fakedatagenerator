package Data.TableConstraints;

import Data.Exceptions.ForeignKeyConstraintException;
import Entities.Table;

import java.util.HashSet;
import java.util.Set;

public class ForeignKeyConstraint implements TableConstraint{
    private Set<Object> foreignKeyValues = new HashSet<>();
    private final Table foreignTable;
    private final String foreignColumn;
    public ForeignKeyConstraint(Table foreignTable, String foreignColumn) {
        this.foreignTable = foreignTable;
        this.foreignColumn = foreignColumn;
    }
    private void addValue(Object value) {
        foreignKeyValues.add(value);
    }
    @Override
    public boolean isValid(Object value) {
        // TODO: Find a way to update the foreign key values from the foreign table
        if (this.foreignKeyValues.contains(value)) {
            return true;
        }
        throw new ForeignKeyConstraintException("Value does not exist in the foreign key values");
    }
}
