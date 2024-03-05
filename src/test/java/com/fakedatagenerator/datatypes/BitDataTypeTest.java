package com.fakedatagenerator.datatypes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BitDataTypeTest {

  private BitDataType bitDataType;

  @BeforeEach
  public void setUp() {
    this.bitDataType = new BitDataType();
  }

  @Test
  public void cast_trueValues_castCorrectly() {
    assertDoesNotThrow(() -> bitDataType.cast("1"));
    assertDoesNotThrow(() -> bitDataType.cast("yes"));
    assertDoesNotThrow(() -> bitDataType.cast("true"));
    assertDoesNotThrow(() -> bitDataType.cast("on"));
    assertDoesNotThrow(() -> bitDataType.cast(null));
  }

  @Test
  public void cast_falseValues_castCorrectly() {
    assertDoesNotThrow(() -> bitDataType.cast("0"));
    assertDoesNotThrow(() -> bitDataType.cast("no"));
    assertDoesNotThrow(() -> bitDataType.cast("false"));
    assertDoesNotThrow(() -> bitDataType.cast("off"));
  }
}
