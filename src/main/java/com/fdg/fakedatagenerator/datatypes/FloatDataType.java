package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class FloatDataType implements DataType<Float> {
  @Override
  public Object store(Object value) throws MismatchedDataTypeException {
    return null;
  }

  @Override
  public Float cast(Object value) throws DeserializationException {
    return 0f;
  }

  @Override
  public boolean validate(String value) {
    return false;
  }
}
