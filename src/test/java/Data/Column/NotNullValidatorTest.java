package Data.Column;

import Data.DataType.DataType;
import Data.Exceptions.NotNullConstraintException;
import Data.Validators.ColumnValidators.NotNullValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The type Not null constraint test.
 */
class NotNullValidatorTest {
    /**
     * The Test column.
     */
    Column testColumn = new Column("Test", DataType.INT, new NotNullValidator());

    /**
     * Null input throws not null constraint exception.
     */
    @Test
    public void nullInputThrowsNotNullConstraintException() {
        Assertions.assertThrows(NotNullConstraintException.class, () -> testColumn.isValid(null));
    }

    /**
     * Not null input throws no exception.
     */
    @Test
    public void notNullInputThrowsNoException() {
        Assertions.assertDoesNotThrow(() -> testColumn.isValid(1));
    }
}