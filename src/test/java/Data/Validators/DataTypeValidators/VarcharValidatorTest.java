package Data.Validators.DataTypeValidators;

import Data.Exceptions.MismatchedDataTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class VarcharValidatorTest {
    private final VarcharValidator varcharValidator = new VarcharValidator();
    @Test

    public void nonStringValueThrowsMismatchedDataTypeException() {
        assertThrows(MismatchedDataTypeException.class, () ->
                varcharValidator.validate(1)
        );
    }
}