package Data.Column;

import Data.DataType;
import Data.Exceptions.NotNullConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotNullConstraintTest {
    Column testColumn = new Column("Test", DataType.INT, new NotNullConstraint());

    @Test
    public void nullInputThrowsNotNullConstraintException() {
        Assertions.assertThrows(NotNullConstraintException.class, () -> testColumn.addValue(null));
    }
}