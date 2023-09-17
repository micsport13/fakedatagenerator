package Data.Validators.TableValidators;

import Data.Column.Column;
import Data.Entities.Entity;
import Data.Exceptions.ForeignKeyConstraintException;
import Data.Table.Table;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Foreign key constraint.
 */
public class ForeignKeyValidator implements TableValidator {
    private Set<Object> foreignKeyValues = new HashSet<>();
    private final Table foreignTable;
    private final String foreignColumn;

    /**
     * Instantiates a new Foreign key constraint.
     *
     * @param foreignTable  the foreign table
     * @param foreignColumn the foreign column
     */
    public ForeignKeyValidator(Table foreignTable, String foreignColumn) {
        this.foreignTable = foreignTable;
        this.foreignColumn = foreignColumn;
    }
    private void addValue(Object value) {
        foreignKeyValues.add(value);
    }
    @Override
    public boolean validate(Object value) {
        Column column = foreignTable.getColumnByName(foreignColumn);
        for (Entity entity: this.foreignTable.getEntities()) {
            this.addValue(entity.getColumnValueMapping().get(column));
        }
        if (this.foreignKeyValues.contains(value)) {
            return true;
        }
        throw new ForeignKeyConstraintException("Value does not exist in the foreign key values");
    }
    @Override
    public String toString() {
        return "Foreign Key: " + this.foreignTable.getName() + "." + this.foreignColumn;
    }
}
