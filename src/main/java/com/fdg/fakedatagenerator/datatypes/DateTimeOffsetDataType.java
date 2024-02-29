package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.time.ZonedDateTime;

public class DateTimeOffsetDataType implements DataType<ZonedDateTime> {

  @Override
  public ZonedDateTime cast(Object value)
      throws MismatchedDataTypeException { // TODO: Implement this class
    return null;
  }
}
