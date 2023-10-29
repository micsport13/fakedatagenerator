package com.fdg.fakedatagenerator.validators.ColumnValidators;

import com.fdg.fakedatagenerator.datatypes.DecimalDataType;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.validators.ConstraintType;
import com.fdg.fakedatagenerator.validators.Validator;
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
        assertTrue(ColumnValidatorFactory.createValidator(new IntegerDataType(), 0, 10) instanceof ColumnCheckValidator);
    }

    @Test
    public void createValidator_withMaxValueOnly_ReturnsValidCheckValidator() {
        Validator maxCheckValidator = ColumnValidatorFactory.createValidator(new IntegerDataType(), null, 10);
        assertTrue(maxCheckValidator instanceof ColumnCheckValidator);
    }

    @Test
    public void createValidator_withNullRange_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ColumnValidatorFactory.createValidator(new DecimalDataType(),
                null, null));
    }

    @Test
    public void createValidator_withMinValueOnly_ThrowsNoException() {
        Validator minCheckValidator = ColumnValidatorFactory.createValidator(new IntegerDataType(), 1, null);
        assertTrue(minCheckValidator instanceof ColumnCheckValidator);
    }

    @Test
    public void createValidator_withNullDataType_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ColumnValidatorFactory.createValidator((String) null));
    }

    @Test
    public void createValidator_allNulls_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ColumnValidatorFactory.createValidator(null, null, null));
    }
}
