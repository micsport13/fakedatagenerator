package com.fdg.fakedatagenerator.datatypes;

import static org.junit.jupiter.api.Assertions.*;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DecimalDataTypeTest {

  DecimalDataType decimalDataType;

  @BeforeEach
  public void setUp() {
    this.decimalDataType = new DecimalDataType();
  }

  @Test
  public void store_withValidInput_ThrowsNoException() {
    assertDoesNotThrow(
        () -> {
          this.decimalDataType.store(1.1);
        });
  }

  @Test
  public void store_withString_ThrowsNoException() {
    assertDoesNotThrow(
        () -> {
          this.decimalDataType.store("1.1");
        });
  }

  @Test
  public void store_withUncastableString_ThrowsMismatchedDataTypeException() {
    assertThrows(MismatchedDataTypeException.class, () -> this.decimalDataType.store("test value"));
  }

  @Test
  public void store_withIntegerString_StoresWithCorrectPrecision() {
    String val = this.decimalDataType.store("1.00000001");
    assertEquals("1", val);
  }

  @Test
  public void store_withNullValue_ReturnsNull() {
    assertNull(this.decimalDataType.store(null));
  }
}
