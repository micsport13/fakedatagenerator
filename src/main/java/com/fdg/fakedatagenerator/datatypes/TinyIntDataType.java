package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class TinyIntDataType implements DataType<Short> {
  @Override
  public Short cast(Object value) throws MismatchedDataTypeException {
    return null;
  }
}
