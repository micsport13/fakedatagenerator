package Data.Validators.TableValidators;

import Data.Column.Column;
import Data.Table.Table;

/**
 * The type Table check constraint.
 */
public class TableCheckValidator implements TableValidator {
    private final Column otherColumn;
    private final Table currentTable;

    public TableCheckValidator(Table table, Column tableColumn) {
        this.otherColumn = tableColumn;
        this.currentTable = table;
    }

    @Override
    public boolean validate(Object value) {
        return false;
        // TODO: handle the checking
        // Should correspond to making sure an entity is created with the correct values in all of the objects.

        //  this.currentTable.getColumnValues(this.otherColumn.getName());
    }

    @Override
    public String toString() {
        return "Check"; // TODO: Update with attributes once defined
    }
}
