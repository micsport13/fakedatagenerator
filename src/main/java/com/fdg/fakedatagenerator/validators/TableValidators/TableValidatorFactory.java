package com.fdg.fakedatagenerator.validators.TableValidators;

import com.fdg.fakedatagenerator.table.Table;
import com.fdg.fakedatagenerator.validators.TableLevelConstraints;
import com.fdg.fakedatagenerator.validators.Validator;

public class TableValidatorFactory {

    public static Validator createValidator(TableLevelConstraints constraintType) {
        return switch (constraintType) {
            case PRIMARY_KEY -> new PrimaryKeyValidator();
            case FOREIGN_KEY ->
                    throw new IllegalArgumentException("To create a foreign key, you must pass in the foreign table and column");// ForeignKeyValidator(); // TODO: Figure out how to implement these
            case UNIQUE -> new UniqueValidator();
            case CHECK ->
                    throw new IllegalArgumentException("To create a check, you must pass in values to apply to the check");
        };
    }


    public static Validator createValidator(TableLevelConstraints constraintType, Table foreignTable, String columnName) {
        if (constraintType != TableLevelConstraints.FOREIGN_KEY) {
            throw new IllegalArgumentException("Cannot pass a table to a non-foreign key constraint");
        }
        return new ForeignKeyValidator(foreignTable, columnName);
    }
}
