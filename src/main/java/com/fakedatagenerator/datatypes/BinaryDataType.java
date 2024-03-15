package com.fakedatagenerator.datatypes;

import com.fakedatagenerator.exceptions.MismatchedDataTypeException;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.nio.charset.StandardCharsets;

public class BinaryDataType implements DataType<byte[]> {

  @Override
  public byte[] cast(Object value) throws MismatchedDataTypeException {
    return value.toString().getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public String toString() {
    return "Binary";
  }
}