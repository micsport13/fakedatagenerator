package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class FloatDataType implements DataType<Float> {

  @Override
  public Float cast(Object value) throws MismatchedDataTypeException {
    return 0f;
  }
}
