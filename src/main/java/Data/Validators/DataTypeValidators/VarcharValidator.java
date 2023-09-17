package Data.Validators.DataTypeValidators;

public class VarcharValidator implements DataTypeValidator {
    @Override
    public boolean validate(Object value) {
        return !(value instanceof Number);
    }
}
