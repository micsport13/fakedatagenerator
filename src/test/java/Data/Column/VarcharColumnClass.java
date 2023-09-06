package Data.Column;

import Data.DataType;
import Data.Exceptions.CheckConstraintException;
import Data.Exceptions.MismatchedDataTypeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The type Varchar column class.
 */
public class VarcharColumnClass {
    private Column varcharColumn;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        varcharColumn = new Column("varchar", DataType.VARCHAR);
    }

    /**
     * Non string value throws mismatched data type exception.
     */
    @Test
    public void nonStringValueThrowsMismatchedDataTypeException() {
        Assertions.assertThrows(MismatchedDataTypeException.class, () -> {
            varcharColumn.isValid(1);
        });
    }

}
