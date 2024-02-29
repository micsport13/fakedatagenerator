package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class NVarcharDataType implements DataType<String> {
  @Override
  public String cast(Object value)
      throws MismatchedDataTypeException { // TODO: Implement this class
    return null;
  }
}
