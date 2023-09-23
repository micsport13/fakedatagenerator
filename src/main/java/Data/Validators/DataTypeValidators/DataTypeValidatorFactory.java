package Data.Validators.DataTypeValidators;

import Data.DataType.DataType;

public class DataTypeValidatorFactory {
    public static DataTypeValidator createValidator(DataType dataType) {
        return switch (dataType) {
            case BOOLEAN -> new BooleanValidator();
            case INT -> new IntValidator();
            case VARCHAR -> new VarcharValidator();
            case DATETIME2 ->  new DateTime2Validator();
            case DATETIMEOFFSET -> new DateTimeOffsetValidator();
            case FLOAT -> new FloatValidator();
        };
    }
}
