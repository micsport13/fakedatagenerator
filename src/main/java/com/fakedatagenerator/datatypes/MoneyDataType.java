package com.fakedatagenerator.datatypes;

import com.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class MoneyDataType implements DataType<Short> {
  @Override
  public Short cast(Object value) throws MismatchedDataTypeException {
    throw new UnsupportedOperationException();
  }
} // TODO: Implement this class
