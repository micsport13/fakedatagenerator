package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class TextDataType implements DataType<String> {

  @Override
  public String cast(Object value) throws MismatchedDataTypeException {
    return null;
  }
}
