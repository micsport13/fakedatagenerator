package com.fakedatagenerator.datatypes;

import com.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.time.ZonedDateTime;

public class DateTimeOffsetDataType implements DataType<ZonedDateTime> {

  @Override
  public ZonedDateTime cast(Object value)
      throws MismatchedDataTypeException { // TODO: Implement this class
    return null;
  }

  @Override
  public String toString() {
    return "DateTimeOffset";
  }
}
