package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public interface DataType<T> {
  String store(Object value) throws MismatchedDataTypeException;

  T cast(Object value) throws DeserializationException;

  boolean validate(String value);
}
