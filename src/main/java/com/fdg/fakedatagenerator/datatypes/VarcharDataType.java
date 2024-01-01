package com.fdg.fakedatagenerator.datatypes;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
@JsonTypeName("varchar")
public class VarcharDataType implements DataType<String> {

  @JsonProperty("max_length")
  @JsonAlias("maxLength")
  private final Integer maxLength;

  public VarcharDataType() {
    this.maxLength = 1;
  }

  public VarcharDataType(Integer maxLength) {
    this.maxLength = maxLength;
  }

  @Override
  public Object store(Object value) {
    if (value == null) {
      return null;
    }
    if (value.toString().length() > this.maxLength) {
      log.warn(
          "Value \"{}\" is longer than max length of {}.  Value will be truncated to stay within maximum string length",
          value,
          this.maxLength);
    }
    return value.toString().length() > maxLength
        ? value.toString().substring(0, this.maxLength)
        : value.toString();
  }

  @Override
  public String cast(
      Object value) { // TODO: Test this function with more inputs to make sure it works
    if (value.toString().length() > this.maxLength) {
      return value.toString().substring(0, this.maxLength);
    }
    return value.toString();
  }

  @Override
  public boolean validate(String value) throws MismatchedDataTypeException {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    return o != null && getClass() == o.getClass();
  }

  @Override
  public String toString() {

    return String.format("Varchar(%d)", this.maxLength);
  }
}
