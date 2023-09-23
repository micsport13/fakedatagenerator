package Data.Validators.DataTypeValidators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

class DateTime2ValidatorTest {
    private final DateTime2Validator dateTime2Validator = new DateTime2Validator();

    @Test
    public void stringWithNoTimeZoneThrowsNoException() {
        Assertions.assertDoesNotThrow(() -> dateTime2Validator.validate("2020-01-01T00:00:00.000Z"));
    }

    /**
     * String with time zone throws no exception.
     */
    @Test
    public void stringWithTimeZoneThrowsNoException() {
        Assertions.assertDoesNotThrow(() -> dateTime2Validator.validate("2020-01-01T00:00:00.000+00:00"));
    }

    /**
     * Date teime throws no exception.
     */
    @Test
    public void dateTeimeThrowsNoException() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        Assertions.assertDoesNotThrow(() -> dateTime2Validator.validate(zonedDateTime));
    }

    /**
     * Valid input of parameterized zoned date time throws no exception.
     */
    @Test
    public void validInputOfParameterizedZonedDateTimeThrowsNoException() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));
        Assertions.assertDoesNotThrow(() -> dateTime2Validator.validate(zonedDateTime));
    }

}