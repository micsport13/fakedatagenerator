package Data.Validators.DataTypeValidators;

public class IntValidator implements DataTypeValidator {
    @Override
    public boolean validate(Object value) {
        if (value instanceof String) {
            try {
                Integer integer = Integer.parseInt((String) value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return value instanceof Number;
    }
}
