package Data.Validators.DataTypeValidators;

import static org.junit.jupiter.api.Assertions.assertFalse;

class VarcharValidatorTest {
    private final VarcharValidator varcharValidator = new VarcharValidator();

    public void nonStringValueThrowsMismatchedDataTypeException() {
        assertFalse(
                varcharValidator.validate(1)
        );
    }
}