package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public class VarcharDataType implements DataType<String> {

  private final Integer maxLength;

  public VarcharDataType() {
    this.maxLength = 1;
  }

  public VarcharDataType(Integer maxLength) {
    this.maxLength = maxLength;
  }

  @Override
  public String store(Object value) {
    if (value == null) {
      return null;
    }
    if (value.toString().length() > this.maxLength) {
      log.warn(
          "Value \"{}\" is longer than max length of {}.  Value will be truncated to stay within maximum string length",
          value,
          this.maxLength);
    }
    return value.toString().substring(0, this.maxLength);
  }

  @Override
  public String cast(Object value) {
    return value.toString().substring(0, this.maxLength);
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
}
