package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.math.BigInteger;

public class BigIntDataType implements DataType<Long> {
  @Override
  public Object store(Object value) throws MismatchedDataTypeException {
    try {
      return new BigInteger(value.toString());
    } catch (NumberFormatException e) {
      throw new MismatchedDataTypeException("BigIntDataType: Cannot store " + value + " as BigInt");
    }
  }

  @Override
  public Long cast(Object value) throws DeserializationException {
    try {
      return new BigInteger(value.toString()).longValueExact();
    } catch (NumberFormatException e) {
      throw new DeserializationException("BigIntDataType: Cannot cast " + value + " to Long");
    }
  }

  @Override
  public boolean validate(String value) {
    try {
      this.store(value);
    } catch (MismatchedDataTypeException e) {
      return false;
    }
    return true;
  }
}
