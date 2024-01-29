package com.fdg.fakedatagenerator.datatypes;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BigIntDataTypeTest {
  private BigIntDataType bigIntDataType;

  @BeforeEach
  void setUp() {
    this.bigIntDataType = new BigIntDataType();
  }

  @Test
  public void cast_WithValidString_ReturnsLong() {
    assertEquals(1L, bigIntDataType.cast("1"));
    assertEquals(0L, bigIntDataType.cast("0"));
    assertEquals(-1L, bigIntDataType.cast("-1"));
    assertEquals(123456789L, bigIntDataType.cast("123456789"));
    assertEquals(-123456789L, bigIntDataType.cast("-123456789"));
    assertEquals(1L, bigIntDataType.cast(new BigInteger("1")));
  }
}
