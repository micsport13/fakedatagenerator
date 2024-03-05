package com.fakedatagenerator.datatypes;

import com.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.util.UUID;

public class UniqueIdentifierDataType implements DataType<UUID> {

  @Override
  public UUID cast(Object value) throws MismatchedDataTypeException { // TODO: Implement this class
    try {
      return UUID.fromString(value.toString());
    } catch (IllegalArgumentException e) {
      throw new MismatchedDataTypeException("Invalid UUID format for value: " + value);
    }
  }

  @Override
  public String toString() {
    return "UniqueIdentifier";
  }
}
