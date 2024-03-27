package com.fakedatagenerator.datatypes;

import com.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class SmallIntDataType implements DataType<Short> {

  // SmallInt covers from -32768 to 32767
  @Override
  public Short cast(Object value) throws MismatchedDataTypeException { // TODO: Implement this class
    throw new UnsupportedOperationException();
  }

  @Override
  public String toString() {
    return "SmallInt";
  }
}
