package com.fakedatagenerator.datatypes;

import com.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.sql.Time;

public class TimeDataType implements DataType<Time> {

  @Override
  public Time cast(Object value) throws MismatchedDataTypeException { // TODO: Implement this class
    throw new UnsupportedOperationException();
  }

  @Override
  public String toString() {
    return "Time";
  }
}
