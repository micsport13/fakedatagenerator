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
            default -> throw new IllegalArgumentException("Invalid constraint type: " + constraintType);
        };
    }

    public static Validator createValidator(ConstraintType constraintType, DataType datatype, Number minValue, Number maxValue) {
        if (constraintType != ConstraintType.CHECK) {
            throw new IllegalArgumentException("Cannot pass ranges to a non-check constraint");
        }
        if (minValue != null && maxValue != null) {
            return new ColumnCheckValidator.CheckConstraintBuilder(datatype).withRange(minValue, maxValue).build();
        }
        if (minValue != null) {
            return new ColumnCheckValidator.CheckConstraintBuilder(datatype).withMinimumValue(minValue).build();
        }
        if (maxValue != null) {
            return new ColumnCheckValidator.CheckConstraintBuilder(datatype).withMaximumValue(maxValue).build();
        }
        throw new IllegalArgumentException("Unable to create numeric check constraint");
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
