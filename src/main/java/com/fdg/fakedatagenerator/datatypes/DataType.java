package com.fdg.fakedatagenerator.datatypes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "name")
@JsonSubTypes({
  @JsonSubTypes.Type(
      value = IntegerDataType.class,
      names = {"integer", "int"}),
  @JsonSubTypes.Type(value = VarcharDataType.class, name = "varchar"),
  @JsonSubTypes.Type(value = DecimalDataType.class, name = "decimal")
})
public interface DataType<T> {
  Object store(Object value) throws MismatchedDataTypeException;

  T cast(Object value) throws DeserializationException;

  boolean validate(String value);
}
