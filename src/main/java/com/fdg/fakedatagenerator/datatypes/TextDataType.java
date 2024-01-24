package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class TextDataType implements DataType<String> {
  @Override
  public Object store(Object value) throws MismatchedDataTypeException {
    return null;
  }

  @Override
  public String cast(Object value) throws DeserializationException {
    return null;
  }

  @Override
  public boolean validate(String value) {
    return false;
  }
}
