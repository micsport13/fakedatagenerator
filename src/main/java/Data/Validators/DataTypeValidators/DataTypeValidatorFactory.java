package Data.Validators.DataTypeValidators;

import Data.DataType.DataType;

import static Data.DataType.DataType.*;

public class DataTypeValidatorFactory {
    public static DataTypeValidator getValidator(DataType dataType) {
        switch (dataType) {
            case BOOLEAN -> {
                return new BooleanValidator();
            }
            case INT -> {
                return new IntValidator();
            }
            case VARCHAR -> {
                return new VarcharValidator();
            }
            case DATETIME2 -> {
                return new DateTime2Validator();
            }
            case DATETIMEOFFSET -> {
                return new DateTimeOffsetValidator();
            }
            case FLOAT -> {
                return new FloatValidator();
            }
            default -> {
                throw new IllegalArgumentException("Invalid data type");
            }
        }
    }
}