package com.fdg.fakedatagenerator.datatypes;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DateTime2DataTypeTest {
  DateTime2DataType dateDataType = new DateTime2DataType();

  @BeforeEach
  void setUp() {}

  @Test
  public void cast_WithValidString_ReturnsDate() {
    assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0), dateDataType.cast("2021-01-01T00:00:00"));
    assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0), dateDataType.cast("2021-01-01T00:00:00"));
    // assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0), dateDataType.cast("2021-01-01 00:00:00Z"));
    assertEquals(
        LocalDateTime.of(2021, 1, 1, 0, 0), dateDataType.cast("2021-01-01T00:00:00+00:00"));
    assertEquals(
        LocalDateTime.of(2021, 1, 1, 0, 0), dateDataType.cast("2021-01-01T00:00:00-00:00"));
  }
}
