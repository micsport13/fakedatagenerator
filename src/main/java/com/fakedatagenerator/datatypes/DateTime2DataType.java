package com.fakedatagenerator.datatypes;

import com.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class DateTime2DataType implements DataType<LocalDateTime> {

  private final ZoneOffset zoneOffset;

  public DateTime2DataType() {
    this.zoneOffset = ZoneOffset.UTC;
  }

  public DateTime2DataType(ZoneOffset zoneOffset) {
    this.zoneOffset = zoneOffset;
  }

  @Override
  public LocalDateTime cast(Object value)
      throws MismatchedDataTypeException { // TODO: Implement this class
    try {
      return LocalDateTime.parse(value.toString());
    } catch (DateTimeParseException ignored) {
    }
    try {
      return ZonedDateTime.parse(value.toString()).toLocalDateTime();
    } catch (DateTimeParseException e) {
      throw new MismatchedDataTypeException(
          "DateTime2DataType: Cannot cast " + value + " to LocalDateTime");
    }
  }

  @Override
  public String toString() {
    return "DateTime2";
  }
}
