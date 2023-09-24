package Data.Validators.DataTypeValidators;

import Data.Exceptions.MismatchedDataTypeException;

public class FloatValidator implements DataTypeValidator {
    @Override
    public void validate(Object value) {
        if (value == null) {
            return;
        }
        if (value instanceof String) {
            try {
                Double.parseDouble((String) value);
                return;
            } catch (NumberFormatException e) {
                throw new MismatchedDataTypeException(this.getClass()
                                                              .getSimpleName() + ": Value is not a valid float");
            }
        }
        if (!(value instanceof Number)) {
            throw new MismatchedDataTypeException(this.getClass()
                    .getSimpleName() + ": Value is not a valid float");
        }
    }
}
