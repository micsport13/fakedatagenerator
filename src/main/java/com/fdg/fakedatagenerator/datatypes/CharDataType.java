package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CharDataType implements DataType<String> {

  private final int maxLength;

  public CharDataType() {
    this.maxLength = 1;
  }

  public CharDataType(int maxLength) {
    this.maxLength = maxLength;
  }

  @Override
  public String cast(Object value) throws MismatchedDataTypeException {
    try {
      String stringValue = value.toString();
      if (stringValue.length() > maxLength) {
        return stringValue.substring(0, maxLength);
      } else {
        return stringValue.concat(" ".repeat(maxLength - stringValue.length()));
      }
    } catch (Exception e) {
      log.error(e);
      throw new MismatchedDataTypeException("CharDataType: Cannot cast " + value + " to char[]");
    }
  }

  @Override
  public String toString() {
    return String.format("Char(%d)", maxLength);
  }
}
