package com.fdg.fakedatagenerator.datatypes;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DateDataTypeTest {

  @BeforeEach
  void setUp() {}

  @Test
  public void cast_WithValidString_ReturnsDate() {
    DateDataType dateDataType = new DateDataType();
    assertEquals(LocalDate.of(2021, 1, 1), dateDataType.cast("2021-01-01"));
    assertEquals(LocalDate.of(2021, 1, 1), dateDataType.cast("2021-01-01T00:00:00"));
    assertEquals(LocalDate.of(2021, 1, 1), dateDataType.cast("2021-01-01 00:00:00Z"));
    assertEquals(LocalDate.of(2021, 1, 1), dateDataType.cast("2021-01-01T00:00:00+00:00"));
    assertEquals(LocalDate.of(2021, 1, 1), dateDataType.cast("2021-01-01T00:00:00-00:00"));
    assertEquals(LocalDate.of(2021, 1, 1), dateDataType.cast("2021-01-01 00:00:00.000000000"));
  }
}
