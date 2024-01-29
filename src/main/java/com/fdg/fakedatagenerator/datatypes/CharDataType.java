package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class CharDataType implements DataType<char[]> {

  @Override
  public char[] cast(Object value) throws MismatchedDataTypeException {
    return new char[0];
  }
}
