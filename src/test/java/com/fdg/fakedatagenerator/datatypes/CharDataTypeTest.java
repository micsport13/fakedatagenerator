package com.fdg.fakedatagenerator.datatypes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CharDataTypeTest {

  private CharDataType charDataType;

  @BeforeEach
  void setUp() {
    this.charDataType = new CharDataType(1);
  }

  @Test
  public void cast_WithValidString_ReturnsChar() {
    assertEquals("a", charDataType.cast("a"));
    assertEquals("A", charDataType.cast("A"));
    assertEquals("1", charDataType.cast("1"));
    assertEquals(" ", charDataType.cast(" "));
    assertEquals("a", charDataType.cast("a "));
  }

  @Test
  public void cast_WithExtraLength_ReturnsSpacePaddedCharacters() {
    CharDataType charDataType = new CharDataType(3);
    assertEquals("1  ", charDataType.cast(1));
    assertEquals("0  ", charDataType.cast(0));
    assertEquals("9  ", charDataType.cast(9));
    assertEquals("1.0", charDataType.cast(1.0));
  }

  @Test
  public void cast_WithLongerInputString_ReturnsTruncatedInput() {
    CharDataType charDataType = new CharDataType(3);
    assertEquals("123", charDataType.cast("1234"));
    assertEquals("123", charDataType.cast("12345"));
  }
}
