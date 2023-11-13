package com.fdg.fakedatagenerator.constraints.column;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fdg.fakedatagenerator.constraints.ColumnLevelConstraints;
import com.fdg.fakedatagenerator.constraints.Constraint;
import com.fdg.fakedatagenerator.datatypes.DecimalDataType;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import org.junit.jupiter.api.Test;

public class ColumnConstraintFactoryTest {

    @Test
    public void createConstraint_WithValidInput_ReturnsCorrectConstraint() {
        assertTrue(ColumnConstraintFactory.createConstraint(ColumnLevelConstraints.NOT_NULL) instanceof NotNullConstraint);
    }

    @Test
    public void createConstraint_withValidAcceptedValues_ReturnsValidCheckConstraint() {
        assertTrue(ColumnConstraintFactory.createConstraint("Test", "Test2") instanceof ColumnCheckConstraint);
    }

    @Test
    public void createConstraint_withRangeValues_ReturnsValidCheckConstraint() {
        assertTrue(ColumnConstraintFactory.createConstraint(new IntegerDataType(), 0, 10) instanceof ColumnCheckConstraint);
    }

    @Test
    public void createConstraint_withMaxValueOnly_ReturnsValidCheckConstraint() {
        Constraint maxCheckConstraint = ColumnConstraintFactory.createConstraint(new IntegerDataType(), null, 10);
        assertTrue(maxCheckConstraint instanceof ColumnCheckConstraint);
    }

    @Test
    public void createConstraint_withNullRange_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ColumnConstraintFactory.createConstraint(new DecimalDataType(),
                null, null));
    }

    @Test
    public void createConstraint_withMinValueOnly_ThrowsNoException() {
        Constraint minCheckConstraint = ColumnConstraintFactory.createConstraint(new IntegerDataType(), 1, null);
        assertTrue(minCheckConstraint instanceof ColumnCheckConstraint);
    }

    @Test
    public void createConstraint_withNullDataType_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ColumnConstraintFactory.createConstraint((String) null));
    }

    @Test
    public void createConstraint_allNulls_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ColumnConstraintFactory.createConstraint(null, null, null));
    }
}
