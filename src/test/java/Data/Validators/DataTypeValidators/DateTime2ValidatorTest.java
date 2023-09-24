package Data.Validators.DataTypeValidators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DateTime2ValidatorTest {
    private final DateTime2Validator dateTime2Validator = new DateTime2Validator();

    @Test
    public void validate_StringWithNoTimeZone_ThrowsNoException() {
        assertDoesNotThrow(() -> dateTime2Validator.validate("2020-01-01T00:00:00.000Z"));
    }

    /**
     * String with time zone throws no exception.
     */
    @Test
    public void validate_StringWithTimeZone_ThrowsNoException() {
        assertDoesNotThrow(() -> dateTime2Validator.validate("2020-01-01T00:00:00.000+00:00"));
    }

    /**
     * Date teime throws no exception.
     */
    @Test
    public void validate_dateTimeObject_ThrowsNoException() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        assertDoesNotThrow(() -> dateTime2Validator.validate(zonedDateTime));
    }

    /**
     * Valid input of parameterized zoned date time throws no exception.
     */
    @Test
    public void validate_InputOfParameterizedZonedDateTime_ThrowsNoException() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));
        assertDoesNotThrow(() -> dateTime2Validator.validate(zonedDateTime));
    }

    @Test
    public void validate_NullInput_ThrowsNoException() {
        assertDoesNotThrow(() -> dateTime2Validator.validate(null));
    }

}