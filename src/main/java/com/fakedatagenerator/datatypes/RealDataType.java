package com.fakedatagenerator.datatypes;

import com.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class RealDataType implements DataType<Float> {

  @Override
  public Float cast(Object value) throws MismatchedDataTypeException { // TODO: Implement this class
    throw new UnsupportedOperationException();
  }

  @Override
  public String toString() {
    return "Real";
  }
}
