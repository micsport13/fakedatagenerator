package com.fakedatagenerator.validators.TableValidators;

import com.fakedatagenerator.table.Table;
import com.fakedatagenerator.validators.ConstraintType;
import com.fakedatagenerator.validators.Validator;

public class TableValidatorFactory {

    public static Validator createValidator(ConstraintType constraintType) {
        return switch (constraintType) {
            case PRIMARY_KEY -> new PrimaryKeyValidator();
            case FOREIGN_KEY ->
                    throw new UnsupportedOperationException("To create a foreign key, you must pass in the foreign table and column");// ForeignKeyValidator(); // TODO: Figure out how to implement these
            case UNIQUE -> new UniqueValidator();
            case CHECK ->
                    throw new UnsupportedOperationException("To create a check, you must pass in values to apply to the check");
            case NOT_NULL -> throw new UnsupportedOperationException("To create a not null constraint, use the column validator factory");
        };
    }


    public static Validator createValidator(ConstraintType constraintType, Table foreignTable, String columnName){
        if (constraintType != ConstraintType.FOREIGN_KEY) {
            throw new IllegalArgumentException("Cannot pass a table to a non-foreign key constraint");
        }
        return new ForeignKeyValidator(foreignTable, columnName);
    }
}
