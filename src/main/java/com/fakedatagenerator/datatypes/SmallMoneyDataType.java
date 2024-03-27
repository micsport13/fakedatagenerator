package com.fakedatagenerator.datatypes;

import com.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class SmallMoneyDataType implements DataType<Short> {
  @Override
  public String toString() {
    return "SmallMoney";
  }

  @Override
  public Short cast(Object value) throws MismatchedDataTypeException {
    throw new UnsupportedOperationException();
  }
} // TODO: Implement this class
