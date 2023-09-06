package Data.Column;

import Data.DataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * The type Datetime 2 column class test.
 */
public class Datetime2ColumnClassTest {
    private Column datetime2Column;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        datetime2Column = new Column("datetime2", DataType.DATETIME2);
    }

    /**
     * String with no time zone throws no exception.
     */
    @Test
    public void stringWithNoTimeZoneThrowsNoException() {
        datetime2Column.isValid("2020-01-01T00:00:00.000Z");
    }

    /**
     * String with time zone throws no exception.
     */
    @Test
    public void stringWithTimeZoneThrowsNoException() {
        datetime2Column.isValid("2020-01-01T00:00:00.000+00:00");
    }

    /**
     * Date teime throws no exception.
     */
    @Test
    public void dateTeimeThrowsNoException() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        datetime2Column.isValid(zonedDateTime);
    }

    /**
     * Valid input of parameterized zoned date time throws no exception.
     */
    @Test
    public void validInputOfParameterizedZonedDateTimeThrowsNoException() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2023,1,1,0,0,0,0, ZoneId.of("UTC"));
        datetime2Column.isValid(zonedDateTime);
    }
}
