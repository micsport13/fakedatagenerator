package Data.Validators.ColumnValidators;

import Data.DataType.DataType;
import Data.Validators.ConstraintType;
import Data.Validators.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColumnValidatorFactoryTest {

    @Test
    public void createValidator_WithValidInput_ReturnsCorrectValidator() {
        assertTrue(ColumnValidatorFactory.createValidator(ConstraintType.NOT_NULL) instanceof NotNullValidator);
    }

    @Test
    public void createValidator_withValidAcceptedValues_ReturnsValidCheckValidator() {
        assertTrue(ColumnValidatorFactory.createValidator("Test", "Test2") instanceof ColumnCheckValidator);
    }
    @Test
    public void createValidator_withRangeValues_ReturnsValidCheckValidator() {
        assertTrue(ColumnValidatorFactory.createValidator(DataType.FLOAT, 0, 10) instanceof ColumnCheckValidator);
    }

    @Test
    public void createValidator_withIncorrectDataType_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ColumnValidatorFactory.createValidator(DataType.VARCHAR, 0, 10));
    }

    @Test
    public void createValidator_withMaxValueOnly_ReturnsValidCheckValidator() {
        Validator maxCheckValidator = ColumnValidatorFactory.createValidator(DataType.FLOAT, null, 10);
        assertDoesNotThrow(() -> maxCheckValidator.validate(1));
    }
    @Test
    public void createValidator_withNullRange_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ColumnValidatorFactory.createValidator(DataType.FLOAT, null, null));
    }
    @Test
    public void createValidator_withMinValueOnly_ThrowsNoException() {
        Validator minCheckValidator = ColumnValidatorFactory.createValidator(DataType.FLOAT, 1, null);
        assertDoesNotThrow(() -> minCheckValidator.validate(1));
    }

    @Test
    public void createValidator_allNulls_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,() -> ColumnValidatorFactory.createValidator(null, null, null));
    }
}
