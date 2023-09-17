package Data.Validators.DataTypeValidators;

import Data.Exceptions.MismatchedDataTypeException;

public class IntValidator implements DataTypeValidator {
    @Override
    public boolean validate(Object value) {
        if (value instanceof String) {
            try {
                Integer integer = Integer.parseInt((String) value);
                return true;
            } catch (NumberFormatException e) {
                throw new MismatchedDataTypeException(this.getClass().getSimpleName() + ": Value is not a valid integer");

            }
        }
        if (!(value instanceof Number)) {
            throw new MismatchedDataTypeException(this.getClass().getSimpleName() + ": Value is not a valid integer");
        }
        return true;
    }
}
