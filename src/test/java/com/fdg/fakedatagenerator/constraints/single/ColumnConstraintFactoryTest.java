package com.fdg.fakedatagenerator.constraints.single;

import static org.junit.jupiter.api.Assertions.*;

import com.fdg.fakedatagenerator.datatypes.DecimalDataType;
import org.junit.jupiter.api.Test;

public class ColumnConstraintFactoryTest {

  @Test
  public void createConstraint_withNullRange_ThrowsIllegalArgumentException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> ColumnConstraintFactory.createConstraint(new DecimalDataType(), null, null));
  }

  @Test
  public void createConstraint_allNulls_ThrowsIllegalArgumentException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> ColumnConstraintFactory.createConstraint(null, null, null));
  }
}
