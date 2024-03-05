package com.fakedatagenerator.datatypes;

import com.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class TextDataType implements DataType<String> {

  @Override
  public String cast(Object value)
      throws MismatchedDataTypeException { // TODO: Implement this class
    return null;
  }

  @Override
  public String toString() {
    return "Text";
  }
}
