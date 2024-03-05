package com.fakedatagenerator.datatypes;

import com.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DateDataType implements DataType<LocalDate> {

  private static final List<DateTimeFormatter> formats =
      List.of(
          DateTimeFormatter.ISO_LOCAL_DATE,
          DateTimeFormatter.ISO_OFFSET_DATE,
          DateTimeFormatter.ISO_DATE,
          DateTimeFormatter.ISO_LOCAL_TIME,
          DateTimeFormatter.ISO_OFFSET_TIME,
          DateTimeFormatter.ISO_TIME,
          DateTimeFormatter.ISO_LOCAL_DATE_TIME,
          DateTimeFormatter.ISO_OFFSET_DATE_TIME,
          DateTimeFormatter.ISO_ZONED_DATE_TIME,
          DateTimeFormatter.ISO_DATE_TIME,
          DateTimeFormatter.ISO_ORDINAL_DATE,
          DateTimeFormatter.ISO_WEEK_DATE,
          DateTimeFormatter.ISO_INSTANT,
          DateTimeFormatter.BASIC_ISO_DATE,
          DateTimeFormatter.RFC_1123_DATE_TIME,
          DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"),
          DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.N"));

  @Override
  public LocalDate cast(Object value)
      throws MismatchedDataTypeException { // TODO: Implement this class
    if (value instanceof LocalDate) {
      return (LocalDate) value;
    }
    if (value instanceof Timestamp) {
      Timestamp timestamp = (Timestamp) value;
      return timestamp.toLocalDateTime().toLocalDate();
    }
    for (var formatter : formats) {
      try {
        return LocalDate.parse(value.toString(), formatter);
      } catch (DateTimeParseException ignored) {
      }
    }
    throw new MismatchedDataTypeException("Invalid date format for value: " + value);
  }

  @Override
  public String toString() {
    return "Date";
  }
}
