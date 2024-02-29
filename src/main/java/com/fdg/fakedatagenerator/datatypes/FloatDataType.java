package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class FloatDataType implements DataType<Float> {

  @Override
  public Float cast(Object value) throws MismatchedDataTypeException { // TODO: Implement this class
    try {
      return Float.parseFloat(value.toString());
    } catch (NumberFormatException e) {
      throw new MismatchedDataTypeException("Invalid float format for value: " + value);
    }
  }
}
