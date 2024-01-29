package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.time.LocalDateTime;

public class DateTime2DataType implements DataType<LocalDateTime> {

  @Override
  public LocalDateTime cast(Object value) throws MismatchedDataTypeException {
    return null;
  }
}
