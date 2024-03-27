package com.fakedatagenerator.datatypes.factories;

import com.fakedatagenerator.datatypes.*;
import com.fakedatagenerator.utils.Primitives;
import java.util.Collections;
import java.util.Map;

public class IntegerDataTypeFactory implements DataTypeFactory {

  @Override
  public DataType<?> createDataType(String dataType, Map<String, Object> args) {
    return new IntegerDataType();
  }

  @Override
  // Integer data type has no options so empty map is returned
  public Map<String, Primitives> getOptions() {
    return Collections.emptyMap();
  }
}
