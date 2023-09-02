package Data.Table;

import Data.Column.Column;
import Data.DataType;
import Data.Exceptions.UniqueConstraintException;
import Entities.Entity;
import Entities.Table;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UniqueConstraintTest {
    UniqueConstraint uniqueConstraint;

    @BeforeEach
    public void setUp() {
        this.uniqueConstraint = new UniqueConstraint();
    }

    @Test
    public void addUniqueValueIsValid() {
        Assertions.assertDoesNotThrow(() -> this.uniqueConstraint.addValue(1));
    }
    @Test
    public void addExistingValueThrowsUniqueConstraintException() {
        this.uniqueConstraint.addValue(1);
        Assertions.assertThrows(UniqueConstraintException.class, () -> this.uniqueConstraint.addValue(1));
    }
    @Test
    public void addMultipleUniqueValuesThrowsNoException() {
        this.uniqueConstraint.addValues(1,2,3);
    }
    @Test
    public void addMultipleSameValuesThrowsUniqueConstraintException() {
        Assertions.assertThrows(UniqueConstraintException.class, () -> this.uniqueConstraint.addValues(1,2,1));
    }

}