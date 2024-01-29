package com.fdg.fakedatagenerator.datatypes;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import lombok.extern.log4j.Log4j2;

@JsonTypeName("integer")
@Log4j2
public class IntegerDataType implements DataType<Integer> {

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
        throw new MismatchedDataTypeException("Error deserializing integer value: " + value);
      }
    }
  }

  @Override
  public int hashCode() {
    return this.getClass().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    return o instanceof IntegerDataType;
  }

  @Override
  public String toString() {
    return "Integer";
  }
}
