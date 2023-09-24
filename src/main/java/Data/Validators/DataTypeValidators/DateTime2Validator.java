package Data.Validators.DataTypeValidators;

import Data.Exceptions.MismatchedDataTypeException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class DateTime2Validator implements DataTypeValidator {
    @Override
    public void validate(Object value) {
        if (value == null) {
            return;
        }
        if (value instanceof ZonedDateTime) {
            return;
        }
        if (value instanceof String) {
            try {
                ZonedDateTime.parse((String) value);
            } catch (DateTimeParseException e) {
                throw new MismatchedDataTypeException(this.getClass()
                        .getSimpleName() + ": Value is not a valid datetime2");
            }
            return;
        }
        throw new MismatchedDataTypeException(this.getClass()
                .getSimpleName() + ": Value is not a valid datetime2");
    }
}
