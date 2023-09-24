package Data.Validators.DataTypeValidators;

import Data.DataType.DataType;
import Data.Exceptions.MismatchedDataTypeException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class DateTimeOffsetValidator implements DataTypeValidator {
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
                throw new MismatchedDataTypeException(this.getClass().getSimpleName() + ": Value is not a valid datetimeoffset");
            }

        }
        throw new MismatchedDataTypeException(this.getClass()
                                                      .getSimpleName() + ": Value is not a valid datetimeoffset");
    }
}
