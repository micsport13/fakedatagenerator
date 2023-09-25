package Data.Validators.ColumnValidators;

import Data.Validators.ConstraintType;
import Data.Validators.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertTrue(ColumnValidatorFactory.createValidator(Integer.class, 0, 10) instanceof ColumnCheckValidator);
    }

    @Test
    public void createValidator_withMaxValueOnly_ReturnsValidCheckValidator() {
        Validator maxCheckValidator = ColumnValidatorFactory.createValidator(Integer.class, null, 10);
        assertTrue(maxCheckValidator instanceof ColumnCheckValidator);
    }
    @Test
    public void createValidator_withNullRange_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ColumnValidatorFactory.createValidator(Float.class, null, null));
    }
    @Test
    public void createValidator_withMinValueOnly_ThrowsNoException() {
        Validator minCheckValidator = ColumnValidatorFactory.createValidator(Integer.class, 1, null);
        assertTrue(minCheckValidator instanceof ColumnCheckValidator);
    }

    @Test
    public void createValidator_withNullDataType_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ColumnValidatorFactory.createValidator((String) null));
    }
    @Test
    public void createValidator_allNulls_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,() -> ColumnValidatorFactory.createValidator(null, null, null));
    }
}
