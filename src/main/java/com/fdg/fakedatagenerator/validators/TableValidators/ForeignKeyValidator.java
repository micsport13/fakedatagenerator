package com.fdg.fakedatagenerator.validators.TableValidators;

import com.fdg.fakedatagenerator.exceptions.ForeignKeyConstraintException;
import com.fdg.fakedatagenerator.table.Table;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Foreign key constraint.
 */
public class ForeignKeyValidator implements TableValidator {
    private final Set<Object> foreignKeyValues = new HashSet<>();
    private final Table foreignTable;
    private final String foreignColumnName;

    /**
     * Instantiates a new Foreign key constraint.
     *
     * @param foreignTable      the foreign table
     * @param foreignColumnName the foreign column
     */
    public ForeignKeyValidator(Table foreignTable, String foreignColumnName) {
        this.foreignTable = foreignTable;
        this.foreignColumnName = foreignColumnName;
    }

    private void addValue(Object value) {
        foreignKeyValues.add(value);
    }

    @Override
    public void validate(Object value) {
        this.foreignKeyValues.addAll(this.foreignTable.getColumnValues(foreignColumnName));
        if (this.foreignKeyValues.contains(value)) {
            return;
        }
        throw new ForeignKeyConstraintException("Value does not exist in the foreign key values");
    }

    @Override
    public String toString() {
        return "Foreign Key: " + this.foreignTable.getName() + "." + this.foreignColumnName;
    }
}
