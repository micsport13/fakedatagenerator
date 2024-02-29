package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class TinyIntDataType implements DataType<Short> {
  @Override
  public Short cast(Object value) throws MismatchedDataTypeException { // TODO: Implement this class
    try {
      return Short.parseShort(value.toString());
    } catch (NumberFormatException e) {
      throw new MismatchedDataTypeException("Invalid tinyint format for value: " + value);
    }
  }
}
