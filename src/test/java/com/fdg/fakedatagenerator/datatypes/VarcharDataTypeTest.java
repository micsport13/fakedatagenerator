package com.fdg.fakedatagenerator.datatypes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VarcharDataTypeTest {
  private VarcharDataType varcharDataType;

  @BeforeEach
  public void setUp() {
    this.varcharDataType = new VarcharDataType();
  }

  @Test
  public void store_withValidInput_ThrowsNoException() {
    assertDoesNotThrow(
        () -> {
          this.varcharDataType.store("test value");
        });
  }

  @Test
  public void store_withValidInput_StoresOnlyVarcharMaxLength() {
    var val = this.varcharDataType.store("test value");
    assertEquals(1, val.toString().length());
    assertEquals("t", val);
  }

  @Test
  public void store_withValidNumber_StoresAsString() {
    var val = this.varcharDataType.store(1);
    assertEquals(1, val.toString().length());
    assertEquals("1", val);
  }

  @Test
  public void store_withNull_ReturnsNull() {
    assertNull(this.varcharDataType.store(null));
  }
}
