package Data.Column;

import Data.DataType;
import Data.Exceptions.MismatchedDataTypeException;
import Data.Table.UniqueConstraint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColumnTest {
    public Column intColumn;
    @BeforeEach
    public void setup() {
        intColumn = new Column("int", DataType.INT);
    }
    @Test
    public void addNullConstraint() {
        ColumnConstraint columnConstraint = new NotNullConstraint();
        intColumn.addConstraint(columnConstraint);
        assertTrue(intColumn.getConstraints().contains(columnConstraint));
    }
    @Test
    public void addCheckConstraint() {
        ColumnConstraint columnConstraint = new CheckConstraint.CheckConstraintBuilder().withRange(0,10).build();
        intColumn.addConstraint(columnConstraint);
        assertTrue(intColumn.getConstraints().contains(columnConstraint));
    }
    @Test
    public void columnsWithSameParametersAreEqual() {
        Column column = new Column("int", DataType.INT);
        assertEquals(intColumn, column);
    }

}
