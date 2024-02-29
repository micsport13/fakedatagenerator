package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class SmallIntDataType implements DataType<Short> {

  // SmallInt covers from -32768 to 32767
  @Override
  public Short cast(Object value) throws MismatchedDataTypeException { // TODO: Implement this class
    return null;
  }

  @Override
  public String toString() {
    return "SmallInt";
  }
}
