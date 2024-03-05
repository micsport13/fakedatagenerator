package com.fakedatagenerator.datatypes;

import com.fakedatagenerator.exceptions.MismatchedDataTypeException;

public class BitDataType implements DataType<Boolean> {

  @Override
  public Boolean cast(Object value) throws MismatchedDataTypeException {
    if (value == null) {
      return null;
    } else {
      String valueString = value.toString().toLowerCase();
      if (valueString.equals("1")
          || valueString.equals("yes")
          || valueString.equals("true")
          || valueString.equals("on")) {
        return true;
      } else if (valueString.equals("0")
          || valueString.equals("no")
          || valueString.equals("false")
          || valueString.equals("off")) {
        return false;
      } else {
        throw new MismatchedDataTypeException("Cannot cast " + value + " to Boolean");
      }
    }
  }

  @Override
  public String toString() {
    return "Bit";
  }
}
