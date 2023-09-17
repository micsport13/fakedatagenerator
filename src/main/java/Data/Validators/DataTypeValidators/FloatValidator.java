package Data.Validators.DataTypeValidators;

public class FloatValidator implements DataTypeValidator {
    @Override
    public boolean validate(Object value) {
        if (value instanceof String) {
            try{
                Double doubleValue = Double.parseDouble((String) value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return value instanceof Number;
    }
}
