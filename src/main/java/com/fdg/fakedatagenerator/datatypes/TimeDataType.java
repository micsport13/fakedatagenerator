package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.sql.Time;

public class TimeDataType implements DataType<Time> {

  @Override
  public Time cast(Object value) throws MismatchedDataTypeException { // TODO: Implement this class
    return null;
  }

  @Override
  public String toString() {
    return "Time";
  }
}
