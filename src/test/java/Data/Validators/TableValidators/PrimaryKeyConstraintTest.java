package Data.Validators.TableValidators;

import Data.Exceptions.PrimaryKeyConstraintException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The type Primary key constraint test.
 */
class PrimaryKeyConstraintTest {
    private PrimaryKeyValidator primaryKeyValidator;

    @BeforeEach
    void setUp() {
        this.primaryKeyValidator = new PrimaryKeyValidator();
    }

    @Test
    void addValueNotInPrimaryKeyThrowsNoException() {
        primaryKeyValidator.validate(1);
    }

    @Test
    void addMultipleValuesNotInPrimaryKeyThrowsNoException() {
        primaryKeyValidator.validate(1);
        primaryKeyValidator.validate(2);
        primaryKeyValidator.validate(3);
    }

    @Test
    void addValueInPrimaryKeyThrowsException() {
        primaryKeyValidator.validate(1);
        assertThrows(PrimaryKeyConstraintException.class, () -> primaryKeyValidator.validate(1));
    }


}