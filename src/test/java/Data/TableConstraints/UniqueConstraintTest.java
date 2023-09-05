package Data.TableConstraints;

import Data.Exceptions.UniqueConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UniqueConstraintTest {
    UniqueConstraint uniqueConstraint;

    @BeforeEach
    public void setUp() {
        this.uniqueConstraint = new UniqueConstraint();
    }

    @Test
    public void addUniqueValueIsValid() {
        Assertions.assertDoesNotThrow(() -> this.uniqueConstraint.isValid(1));
    }
    @Test
    public void addExistingValueThrowsUniqueConstraintException() {
        this.uniqueConstraint.isValid(1);
        Assertions.assertThrows(UniqueConstraintException.class, () -> this.uniqueConstraint.isValid(1));
    }

}