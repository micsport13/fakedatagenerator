package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class RealDataType implements DataType<Float> {
  @Override
  public Object store(Object value) throws MismatchedDataTypeException {
    return null;
  }

  @Override
  public Float cast(Object value) throws DeserializationException {
    return null;
  }

  @Override
  public boolean validate(String value) {
    return false;
  }
}
