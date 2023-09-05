package Data.Column;

import Data.DataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Datetime2ColumnClassTest {
    private Column datetime2Column;
    @BeforeEach
    public void setup() {
        datetime2Column = new Column("datetime2", DataType.DATETIME2);
    }
    @Test
    public void stringWithNoTimeZoneThrowsNoException() {
        datetime2Column.isValid("2020-01-01T00:00:00.000Z");
    }
    @Test
    public void stringWithTimeZoneThrowsNoException() {
        datetime2Column.isValid("2020-01-01T00:00:00.000+00:00");
    }
    @Test
    public void dateTeimeThrowsNoException() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        datetime2Column.isValid(zonedDateTime);
    }
    @Test
    public void validInputOfParameterizedZonedDateTimeThrowsNoException() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2023,1,1,0,0,0,0, ZoneId.of("UTC"));
        datetime2Column.isValid(zonedDateTime);
    }
}
