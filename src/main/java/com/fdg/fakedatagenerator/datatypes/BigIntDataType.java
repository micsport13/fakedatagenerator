package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.math.BigDecimal;

public class BigIntDataType implements DataType<Long> {
  private static final DataTypeGroup dataTypeGroup = DataTypeGroup.NUMERIC;

  @Override
  public Long cast(Object value) throws MismatchedDataTypeException {
    try {
      return new BigDecimal(value.toString()).longValueExact();
    } catch (NumberFormatException e) {
      throw new MismatchedDataTypeException("BigIntDataType: Cannot cast " + value + " to Long");
    }
  }
}
