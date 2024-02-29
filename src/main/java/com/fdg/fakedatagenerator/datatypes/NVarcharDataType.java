package com.fdg.fakedatagenerator.datatypes;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class NVarcharDataType implements DataType<String> {
  @JsonProperty("max_length")
  @JsonAlias("maxLength")
  private final Integer maxLength;

  public NVarcharDataType() {
    this.maxLength = 1;
  }

  public NVarcharDataType(Integer maxLength) {
    this.maxLength = maxLength;
  }

  @Override
  public String cast(Object value)
      throws MismatchedDataTypeException { // TODO: Implement this class
    return null;
  }

  @Override
  public String toString() {
    return String.format("NVarchar(%d)", this.maxLength);
  }
}
