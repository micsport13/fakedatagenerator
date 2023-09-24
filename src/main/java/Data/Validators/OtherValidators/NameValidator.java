package Data.Validators.OtherValidators;

import Data.Validators.Validator;
import java.util.regex.Pattern;

public class NameValidator {
    public static final Pattern NAME_VALIDATION = Pattern.compile("[A-z]\\w+"); //TODO: Verify if this is the valid naming for tables/columns

    public static void validate(Object value) {
        if (!(value instanceof String)) {
            throw new IllegalArgumentException("Name must be a string");
        }
        if (!NAME_VALIDATION.matcher((String) value).matches()) {
            throw new IllegalArgumentException("Name is not valid");
        }
    }
}
