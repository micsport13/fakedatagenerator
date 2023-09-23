package Data.Validators.DataTypeValidators;

import Data.DataType.DataType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataTypeValidatorFactoryTest {
    @Test
    void testCreateValidator() {
        DataTypeValidator booleanValidator = DataTypeValidatorFactory.createValidator(DataType.BOOLEAN);
        assertTrue(booleanValidator instanceof BooleanValidator);

        DataTypeValidator intValidator = DataTypeValidatorFactory.createValidator(DataType.INT);
        assertTrue(intValidator instanceof IntValidator);

        DataTypeValidator varcharValidator = DataTypeValidatorFactory.createValidator(DataType.VARCHAR);
        assertTrue(varcharValidator instanceof VarcharValidator);

        DataTypeValidator dateTime2Validator = DataTypeValidatorFactory.createValidator(DataType.DATETIME2);
        assertTrue(dateTime2Validator instanceof DateTime2Validator);

        DataTypeValidator dateTimeOffsetValidator = DataTypeValidatorFactory.createValidator(DataType.DATETIMEOFFSET);
        assertTrue(dateTimeOffsetValidator instanceof DateTimeOffsetValidator);

        DataTypeValidator floatValidator = DataTypeValidatorFactory.createValidator(DataType.FLOAT);
        assertTrue(floatValidator instanceof FloatValidator);
    }
}
