package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.math.BigInteger;

public class BigIntDataType implements DataType<Long> {

  @Override
  public Long cast(Object value) throws MismatchedDataTypeException {
    try {
      return new BigInteger(value.toString()).longValueExact();
    } catch (NumberFormatException e) {
      throw new MismatchedDataTypeException("BigIntDataType: Cannot cast " + value + " to Long");
    }
  }
}
