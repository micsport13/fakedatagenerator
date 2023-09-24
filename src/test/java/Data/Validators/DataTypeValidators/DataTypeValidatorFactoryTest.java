package Data.Validators.DataTypeValidators;

import Data.DataType.DataType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataTypeValidatorFactoryTest {
    @Test
    void createValidator_AllValidDataTypes_ReturnsCorrectValidatorType() {
        assertTrue(DataTypeValidatorFactory.createValidator(DataType.BOOLEAN) instanceof BooleanValidator);

        assertTrue(DataTypeValidatorFactory.createValidator(DataType.INT) instanceof IntValidator);

        assertTrue(DataTypeValidatorFactory.createValidator(DataType.VARCHAR) instanceof VarcharValidator);

        assertTrue(DataTypeValidatorFactory.createValidator(DataType.DATETIME2) instanceof DateTime2Validator);

        assertTrue(DataTypeValidatorFactory.createValidator(DataType.DATETIMEOFFSET) instanceof DateTimeOffsetValidator);

        assertTrue(DataTypeValidatorFactory.createValidator(DataType.FLOAT) instanceof FloatValidator);
    }
}
