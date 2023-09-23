package Data.Validators.ColumnValidators;

import Data.DataType.DataType;
import Data.Validators.ConstraintType;
import Data.Validators.Validator;

public class ColumnValidatorFactory {
    public static Validator createValidator(ConstraintType constraintType) {
        return switch(constraintType) {
            case CHECK -> throw new UnsupportedOperationException("When creating a column check constraint, you must provide parameters");
            case NOT_NULL -> new NotNullValidator();
            default -> throw new UnsupportedOperationException("Other constraint types are not supported for the column validator factory");
        };
    }

    public static Validator createValidator(DataType dataType, Number minValue, Number maxValue) {
        if (minValue != null && maxValue != null) {
            return new ColumnCheckValidator.CheckConstraintBuilder(dataType).withRange(minValue, maxValue).build();
        }
        if (minValue != null) {
            return new ColumnCheckValidator.CheckConstraintBuilder(dataType).withMinimumValue(minValue).build();
        }
        if (maxValue != null) {
            return new ColumnCheckValidator.CheckConstraintBuilder(dataType).withMaximumValue(maxValue).build();
        }
        throw new IllegalArgumentException("Unable to create numeric check constraint");
    }
    public static Validator createValidator(String... acceptedValues) {
        return new ColumnCheckValidator.CheckConstraintBuilder(DataType.VARCHAR).withAcceptedValues(acceptedValues).build();
    }


}
