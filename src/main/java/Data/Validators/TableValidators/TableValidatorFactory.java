package Data.Validators.TableValidators;

import Data.DataType.DataType;
import Data.Table.Table;
import Data.Validators.ColumnValidators.ColumnCheckValidator;
import Data.Validators.ConstraintType;
import Data.Validators.Validator;

public class TableValidatorFactory {

    public static Validator createValidator(ConstraintType constraintType) {
        return switch (constraintType) {
            case PRIMARY_KEY -> new PrimaryKeyValidator();
            case FOREIGN_KEY ->
                    throw new IllegalArgumentException("To create a foreign key, you must pass in the foreign table and column");// ForeignKeyValidator(); // TODO: Figure out how to implement these
            case UNIQUE -> new UniqueValidator();
            case CHECK ->
                    throw new IllegalArgumentException("To create a check, you must pass in values to apply to the check");
            case NOT_NULL -> throw new UnsupportedOperationException("To create a not null constraint, use the column validator factory");
            default -> throw new IllegalArgumentException("Invalid constraint type: " + constraintType);
        };
    }


    public static Validator createValidator(ConstraintType constraintType, Table foreignTable, String columnName){
        if (constraintType != ConstraintType.FOREIGN_KEY) {
            throw new IllegalArgumentException("Cannot pass a table to a non-foreign key constraint");
        }
        return new ForeignKeyValidator(foreignTable, columnName);
    }
    public static Validator createValidator(ConstraintType constraintType, String... acceptedValues){
        if (constraintType != ConstraintType.CHECK) {
            throw new IllegalArgumentException("Cannot pass a string array to a non-check constraint");
        }
        return new ColumnCheckValidator.CheckConstraintBuilder(DataType.VARCHAR).withAcceptedValues(acceptedValues).build();
    }
}
