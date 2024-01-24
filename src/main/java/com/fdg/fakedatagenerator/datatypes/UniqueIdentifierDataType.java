package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

import java.util.UUID;

public class UniqueIdentifierDataType implements DataType<UUID> {
  @Override
  public Object store(Object value) throws MismatchedDataTypeException {
    return null;
  }

  @Override
  public UUID cast(Object value) throws DeserializationException {
    return null;
  }

  @Override
  public boolean validate(String value) {
    return false;
  }
}
