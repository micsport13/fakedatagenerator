package com.fakedatagenerator.datatypes;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public class VarcharDataType implements DataType<String> {

  public static Integer DEFAULT_MAX_LENGTH = 1;

  @JsonProperty("max_length")
  @JsonAlias("maxLength")
  private final Integer maxLength;

  public VarcharDataType() {
    this.maxLength = DEFAULT_MAX_LENGTH;
  }

  public VarcharDataType(Integer maxLength) {
    this.maxLength = maxLength;
  }

  @Override
  public String cast( // TODO: Test this function with more inputs to make sure it works
      Object value) {
    if (value == null) {
      return null;
    }
    if (value.toString().length() > this.maxLength) {
      log.warn(
          "Value \"{}\" is longer than max length of {}.  Value will be truncated to stay within maximum string length",
          value,
          this.maxLength);
      return value.toString().substring(0, this.maxLength);
    }
    return value.toString().length() > maxLength
        ? value.toString().substring(0, this.maxLength)
        : value.toString();
  }

  @Override
  public int hashCode() {
    return this.maxLength.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    return o != null
        && this.getClass() == o.getClass()
        && this.maxLength.equals(((VarcharDataType) o).getMaxLength());
  }

  @Override
  public String toString() {

    return String.format("Varchar(%d)", this.maxLength);
  }
}
