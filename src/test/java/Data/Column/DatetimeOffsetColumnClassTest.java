package Data.Column;

import Data.DataType;
import org.junit.jupiter.api.BeforeEach;

/**
 * The type Datetime offset column class test.
 */
public class DatetimeOffsetColumnClassTest {
    private Column datetimeoffsetColumn;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        datetimeoffsetColumn = new Column("datetimeoffset", DataType.DATETIMEOFFSET);
    }
}
