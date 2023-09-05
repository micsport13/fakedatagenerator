package Data.Column;

import Data.DataType;
import Data.Exceptions.NotNullConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NotNullConstraintTest {
    Column testColumn = new Column("Test", DataType.INT, new NotNullConstraint());

    @Test
    public void nullInputThrowsNotNullConstraintException() {
        Assertions.assertThrows(NotNullConstraintException.class, () -> testColumn.isValid(null));
    }
    @Test
    public void notNullInputThrowsNoException() {
        Assertions.assertDoesNotThrow(() -> testColumn.isValid(1));
    }
}