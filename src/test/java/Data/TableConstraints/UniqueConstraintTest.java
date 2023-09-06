package Data.TableConstraints;

import Data.Exceptions.UniqueConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The type Unique constraint test.
 */
class UniqueConstraintTest {
    /**
     * The Unique constraint.
     */
    UniqueConstraint uniqueConstraint;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        this.uniqueConstraint = new UniqueConstraint();
    }

    /**
     * Add unique value is valid.
     */
    @Test
    public void addUniqueValueIsValid() {
        Assertions.assertDoesNotThrow(() -> this.uniqueConstraint.isValid(1));
    }

    /**
     * Add existing value throws unique constraint exception.
     */
    @Test
    public void addExistingValueThrowsUniqueConstraintException() {
        this.uniqueConstraint.isValid(1);
        Assertions.assertThrows(UniqueConstraintException.class, () -> this.uniqueConstraint.isValid(1));
    }

}