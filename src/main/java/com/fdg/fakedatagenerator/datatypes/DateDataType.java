package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.util.Date;

public class DateDataType implements DataType<Date> {

  @Override
  public Date cast(Object value) throws MismatchedDataTypeException {
    return null;
  }
}
