package com.fdg.fakedatagenerator.datatypes;

import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.nio.charset.StandardCharsets;

public class BinaryDataType implements DataType<byte[]> {

  @Override
  public byte[] cast(Object value) throws MismatchedDataTypeException {
    return value.toString().getBytes(StandardCharsets.UTF_8);
  }
}
