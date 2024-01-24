package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class BinaryDataType implements DataType<byte[]> {
  @Override
  public Object store(Object value) throws MismatchedDataTypeException {
    return null;
  }

  @Override
  public byte[] cast(Object value) throws DeserializationException {
    return new byte[0];
  }

  @Override
  public boolean validate(String value) {
    return false;
  }
}
