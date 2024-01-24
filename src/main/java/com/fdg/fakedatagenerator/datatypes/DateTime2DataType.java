package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

import java.time.LocalDateTime;

public class DateTime2DataType implements DataType<LocalDateTime> {
  @Override
  public Object store(Object value) throws MismatchedDataTypeException {
    return null;
  }

  @Override
  public LocalDateTime cast(Object value) throws DeserializationException {
    return null;
  }

  @Override
  public boolean validate(String value) {
    return false;
  }
}
