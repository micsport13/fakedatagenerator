package Data.Column;

import Data.DataType;
import org.junit.jupiter.api.BeforeEach;

public class DatetimeOffsetColumnClassTest {
    private Column datetimeoffsetColumn;

    @BeforeEach
    public void setup() {
        datetimeoffsetColumn = new Column("datetimeoffset", DataType.DATETIMEOFFSET);
    }
}
