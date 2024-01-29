package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class BinaryDataType implements DataType<byte[]> {

  @Override
  public byte[] cast(Object value) throws MismatchedDataTypeException {
    return new byte[0];
  }
}
