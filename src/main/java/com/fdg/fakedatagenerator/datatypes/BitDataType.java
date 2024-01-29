package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class BitDataType implements DataType<Boolean> {

  @Override
  public Boolean cast(Object value) throws MismatchedDataTypeException {
    return null;
  }
}
