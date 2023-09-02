package Data;

import Data.Column.Column;
import Data.Exceptions.MismatchedDataTypeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ColumnTest {
    @Test
    public void addValueWithCorrectDataTypeThrowsNoException() {
        Column column = new Column("id", DataType.INT);
        Assertions.assertDoesNotThrow(() -> column.addValue(1));
    }
    @Test
    public void addValueWithIncorrectDataTypeThrowsDataTypeException() {
        Column column = new Column("id", DataType.INT);
        Assertions.assertThrows(MismatchedDataTypeException.class, () -> column.addValue("1"));
    }
}