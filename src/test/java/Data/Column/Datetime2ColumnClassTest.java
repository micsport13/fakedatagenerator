package Data.Column;

import Data.DataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Datetime2ColumnClassTest {
    private Column datetime2Column;
    @BeforeEach
    public void setup() {
        datetime2Column = new Column("datetime2", DataType.DATETIME2);
    }
    @Test
    public void validInputThrowsNoException() {
        datetime2Column.isValid("2020-01-01T00:00:00.000Z");
    }
    @Test
    public void validInputOfZonedDateTimeThrowsNoException() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        datetime2Column.isValid(zonedDateTime);
    }
}
