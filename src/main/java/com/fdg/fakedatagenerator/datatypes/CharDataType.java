package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class CharDataType implements DataType<char[]> {
  @Override
  public Object store(Object value) throws MismatchedDataTypeException {
    return null;
  }

  @Override
  public char[] cast(Object value) throws DeserializationException {
    return new char[0];
  }

  @Override
  public boolean validate(String value) {
    return false;
  }
}
