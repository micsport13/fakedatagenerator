package com.fdg.fakedatagenerator.validators.TableValidators;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.table.Table;

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
    public void validate(Object value) {
        // TODO: handle the checking
        // Should correspond to making sure an entity is created with the correct values in all of the objects.

        //  this.currentTable.getColumnValues(this.otherColumn.getName());
    }

    @Override
    public String toString() {
        return "Check"; // TODO: Update with attributes once defined
    }
}
