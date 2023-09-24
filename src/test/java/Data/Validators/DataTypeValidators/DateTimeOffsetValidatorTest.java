package Data.Validators.DataTypeValidators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class DateTimeOffsetValidatorTest {
    private final DataTypeValidator dataTimeOffsetValidator = new DateTimeOffsetValidator();
    @Test
    public void validate_NullInput_ThrowsNoException() {
        assertDoesNotThrow(() -> dataTimeOffsetValidator.validate(null));
    }
}
