package Data.Validators.DataTypeValidators;

import java.time.ZonedDateTime;

public class DateTimeOffsetValidator implements DataTypeValidator{
    @Override
    public boolean validate(Object value) {
        if (value instanceof ZonedDateTime) {
            return true;
        }
        if (value instanceof String) {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse((String) value);
            return true;
        }
        return false;
    }
}