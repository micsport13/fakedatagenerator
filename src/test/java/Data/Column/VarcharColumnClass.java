package Data.Column;

import Data.DataType;
import Data.Exceptions.CheckConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VarcharColumnClass {
    private Column varcharColumn;
    @BeforeEach
    public void setup() {
        varcharColumn = new Column("varchar", DataType.VARCHAR);
    }
    @Test
    public void valueInAcceptedValuesThrowsNoException() {
        CheckConstraint checkConstraint = new CheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test", "Test1").build();
        varcharColumn.addConstraint(checkConstraint);
        Assertions.assertDoesNotThrow(() -> varcharColumn.isValid("Test"));
    }
    @Test
    public void valueNotInAcceptedValuesThrowsException() {
        CheckConstraint checkConstraint = new CheckConstraint.CheckConstraintBuilder().withAcceptedValues("Test", "Test1").build();
        varcharColumn.addConstraint(checkConstraint);
        Assertions.assertThrows(CheckConstraintException.class, () -> varcharColumn.isValid("Test3"));
    }
}
