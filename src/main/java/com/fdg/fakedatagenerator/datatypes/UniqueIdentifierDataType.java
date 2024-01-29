package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.util.UUID;

public class UniqueIdentifierDataType implements DataType<UUID> {

  @Override
  public UUID cast(Object value) throws MismatchedDataTypeException {
    return null;
  }
}
