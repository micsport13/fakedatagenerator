package Data.Validators.DataTypeValidators;

import Data.Exceptions.MismatchedDataTypeException;

public class FloatValidator implements DataTypeValidator {
    @Override
    public boolean validate(Object value) {
        if (value instanceof String) {
            try {
                Double doubleValue = Double.parseDouble((String) value);
                return true;
            } catch (NumberFormatException e) {
                throw new MismatchedDataTypeException(this.getClass()
                                                              .getSimpleName() + ": Value is not a valid float");

            }
        }
        if (!(value instanceof Number)) {
            throw new MismatchedDataTypeException(this.getClass()
                                                          .getSimpleName() + ": Value is not a valid float");
        }
        return true;
    }
}
