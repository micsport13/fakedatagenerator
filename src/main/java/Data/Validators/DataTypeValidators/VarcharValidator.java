package Data.Validators.DataTypeValidators;

import Data.Exceptions.MismatchedDataTypeException;

public class VarcharValidator implements DataTypeValidator {
    @Override
    public boolean validate(Object value) {
        if (value instanceof Number) {
            throw new MismatchedDataTypeException(this.getClass()
                                                          .getSimpleName() + ": Value is not a valid Varchar");
        }
        return true;
    }
}
