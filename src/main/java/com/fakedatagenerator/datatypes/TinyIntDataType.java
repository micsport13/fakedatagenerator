package com.fakedatagenerator.datatypes;

import com.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class TinyIntDataType implements DataType<Short> {

  // TinyInt covers from 0 to 255
  // Must use short because Byte is not large enough to cover the range
  @Override
  public Short cast(Object value) throws MismatchedDataTypeException { // TODO: Implement this class
    try {
      return Short.parseShort(
          value
              .toString()); // TODO: This should return a value that is between 0 and 255, not Short
    } catch (NumberFormatException e) {
      throw new MismatchedDataTypeException("Invalid tinyint format for value: " + value);
    }
  }

  @Override
  public String toString() {
    return "TinyInt";
  }
}
