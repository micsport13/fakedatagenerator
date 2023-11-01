package com.fdg.fakedatagenerator.constraints.column;

import com.fdg.fakedatagenerator.constraints.Constraint;
import com.fdg.fakedatagenerator.datatypes.DecimalDataType;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.constraints.ColumnLevelConstraints;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColumnConstraintFactoryTest {

    @Test
    public void createValidator_WithValidInput_ReturnsCorrectValidator() {
        assertTrue(ColumnConstraintFactory.createValidator(ColumnLevelConstraints.NOT_NULL) instanceof NotNullConstraint);
    }

    @Test
    public void createValidator_withValidAcceptedValues_ReturnsValidCheckValidator() {
        assertTrue(ColumnConstraintFactory.createValidator("Test", "Test2") instanceof ColumnCheckConstraint);
    }

    @Test
    public void createValidator_withRangeValues_ReturnsValidCheckValidator() {
        assertTrue(ColumnConstraintFactory.createValidator(new IntegerDataType(), 0, 10) instanceof ColumnCheckConstraint);
    }

    @Test
    public void createValidator_withMaxValueOnly_ReturnsValidCheckValidator() {
        Constraint maxCheckConstraint = ColumnConstraintFactory.createValidator(new IntegerDataType(), null, 10);
        assertTrue(maxCheckConstraint instanceof ColumnCheckConstraint);
    }

    @Test
    public void createValidator_withNullRange_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ColumnConstraintFactory.createValidator(new DecimalDataType(),
                null, null));
    }

    @Test
    public void createValidator_withMinValueOnly_ThrowsNoException() {
        Constraint minCheckConstraint = ColumnConstraintFactory.createValidator(new IntegerDataType(), 1, null);
        assertTrue(minCheckConstraint instanceof ColumnCheckConstraint);
    }

    @Test
    public void createValidator_withNullDataType_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ColumnConstraintFactory.createValidator((String) null));
    }

    @Test
    public void createValidator_allNulls_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ColumnConstraintFactory.createValidator(null, null, null));
    }
}
