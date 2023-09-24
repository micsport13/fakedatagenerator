package Data.Validators.DataTypeValidators;

import Data.Column.Column;
import Data.DataType.DataType;
import Data.Exceptions.MismatchedDataTypeException;

public class BooleanValidator implements DataTypeValidator {

    /**
     * Checks if the value is a valid boolean
     * {@link DataType#BOOLEAN}
     *
     * @param value Object value to be checked for {@link Column#isValid(Object)}
     */
    public void validate(Object value) {
        if (value == null) {
            return;
        }
        if (value instanceof Integer && ((Integer) value == 1 || (Integer) value == 0)) {
            return;
        }
        if (!(value instanceof Boolean)) {
            throw new MismatchedDataTypeException(this.getClass()
                                                          .getSimpleName() + ": Value is not a valid boolean");
        }
    }
}
