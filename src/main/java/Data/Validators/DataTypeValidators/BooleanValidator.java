package Data.Validators.DataTypeValidators;

import Data.Column.Column;
import Data.DataType.DataType;

public class BooleanValidator implements DataTypeValidator {

    /**
     * Checks if the value is a valid boolean
     * {@link DataType#BOOLEAN}
     * @param value Object value to be checked for {@link Column#isValid(Object)}
     * @return
     */
    public boolean validate(Object value) {
        if (value instanceof Integer) {
            return (Integer) value == 0 || (Integer) value == 1;
        }
        return value instanceof Boolean;
    }
}
