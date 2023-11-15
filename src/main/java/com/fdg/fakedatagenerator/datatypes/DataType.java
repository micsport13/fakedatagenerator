package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public interface DataType<T> { // TODO: Does this need types?
  String store(Object value) throws MismatchedDataTypeException;

  T cast(Object value) throws DeserializationException;

  String serialize();
  boolean validate(String value);
}
