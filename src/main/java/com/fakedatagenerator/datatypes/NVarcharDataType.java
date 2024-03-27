package com.fakedatagenerator.datatypes;

import com.fakedatagenerator.exceptions.MismatchedDataTypeException;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NVarcharDataType implements DataType<String> {
  public static Integer DEFAULT_MAX_LENGTH = 1;

  @JsonProperty("max_length")
  @JsonAlias("maxLength")
  private final Integer maxLength;

  public NVarcharDataType() {
    this.maxLength = DEFAULT_MAX_LENGTH;
  }

  public NVarcharDataType(Integer maxLength) {
    this.maxLength = maxLength;
  }

  @Override
  public String cast(Object value)
      throws MismatchedDataTypeException { // TODO: Implement this class
    throw new UnsupportedOperationException();
  }

  @Override
  public String toString() {
    return String.format("NVarchar(%d)", this.maxLength);
  }
}
