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
    var val = this.decimalDataType.store("1.00000001");
    assertEquals("1", val.toString());
  }

  @Test
  public void store_withNullValue_ReturnsNull() {
    assertNull(this.decimalDataType.store(null));
  }

  @Test
  public void store_withScaleAndPrecision_ReturnsCorrectValue() {
    DecimalDataType testDecimal = new DecimalDataType(18, 2);
    var testVal = testDecimal.store(18.203);
    assertEquals("18.20", testVal.toString());
  }

  @Test
  public void store_withLargeScale_ReturnsCorrectValue() {
    DecimalDataType testDecimal = new DecimalDataType(18, 8);
    var testVal = testDecimal.store(18.00000001);
    assertEquals("18.00000001", testVal.toString());
  }

  @Test
  public void store_withLargeValue_ReturnsCorrectValue() {
    DecimalDataType testDecimal = new DecimalDataType(3, 0);
    var testVal = testDecimal.store(18.00000001);
    assertEquals("18", testVal.toString());
  }

  @Test
  public void storeAndCast_withValidInput_ReturnsIdenticalValueAsStored() {
    var testVal = this.decimalDataType.store(1.123580128935792835); // Stores as one
    assertEquals(testVal, this.decimalDataType.cast(testVal)); // Returns as one
  }
}
