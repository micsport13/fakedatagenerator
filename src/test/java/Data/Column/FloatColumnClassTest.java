package Data.Column;

import Data.DataType;
import org.junit.jupiter.api.BeforeEach;

public class FloatColumnClassTest {
    private Column floatColumn;
    @BeforeEach
    public void setup() {
        floatColumn = new Column("float", DataType.FLOAT);
    }
}
