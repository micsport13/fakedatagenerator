package com.fakedatagenerator.datatypes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntegerDataTypeTest {

  IntegerDataType integerDataType;

  @BeforeEach
  public void setUp() {
    this.integerDataType = new IntegerDataType();
  }

  @Test
  public void cast_withValidInput_ThrowsNoException() {
    assertDoesNotThrow(
        () -> {
          this.integerDataType.cast(1);
        });
  }

  @Test
  public void cast_parseableString_ThrowsNoException() {
    assertDoesNotThrow(
        () -> {
          this.integerDataType.cast("1");
        });
  }

  @Test
  public void cast_parseableString_ReturnsIntegerValue() {
    assertEquals(1, this.integerDataType.cast("1"));
  }

  @Test
  public void cast_parseableStringFraction_ReturnsLowerBoundedIntegerValue() {
    assertEquals(1, this.integerDataType.cast("1.5"));
    assertEquals(1, this.integerDataType.cast("1.999999"));
    assertEquals(0, this.integerDataType.cast("0.999999"));
    assertEquals(0, this.integerDataType.cast("-.099999"));
  }

  @Test
  public void cast_double_ReturnsIntegerValue() {
    assertEquals(1, this.integerDataType.cast(1.0));
  }

  @Test
  public void cast_doubleWithFractionalValue_ReturnsLowerBoundedIntegerValue() {
    assertEquals(1, this.integerDataType.cast(1.5));
  }

  @Test
  public void cast_floatWithFractionalValue_ReturnsLowerBoundedIntegerValue() {
    assertEquals(1, this.integerDataType.cast(1.5f));
  }

  @Test
  public void cast_withNullValue_ReturnsNull() {
    assertNull(this.integerDataType.cast(null));
  }
}
