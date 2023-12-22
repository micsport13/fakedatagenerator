package com.fdg.fakedatagenerator.datatypes;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

@JsonTypeName("integer")
public class IntegerDataType implements DataType<Integer> {

  @Override
  public Object store(Object value) throws MismatchedDataTypeException {
    if (value == null) {
      return null;
    }
    try {
      return Integer.parseInt(value.toString());
    } catch (NumberFormatException e) {
      throw new MismatchedDataTypeException("Error casting integer value: " + value);
    }
  }

  @Override
  public Integer cast(Object value) {
    if (value == null) {
      return null;
    }
    if (value instanceof Integer) {
      return (Integer) value;
    } else {
      try {
        double doubleValue = Double.parseDouble(value.toString());
        return (int) doubleValue;
      } catch (NumberFormatException e) {
        throw new DeserializationException("Error deserializing integer value: " + value);
      }
    }
  }

  @Override
  public boolean validate(String value) {
    try {
      Integer.parseInt(value);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    return o != null && getClass() == o.getClass();
  }

  @Override
  public String toString() {
    return "Integer";
  }
}
