package com.fakedatagenerator.datatypes.factories;

import com.fakedatagenerator.datatypes.DataType;
import com.fakedatagenerator.utils.Primitives;
import java.util.Collections;
import java.util.Map;

public class RealDataTypeFactory implements DataTypeFactory {

  @Override
  public DataType<?> createDataType(String dataType, Map<String, Object> args) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Map<String, Primitives> getOptions() {
    return Collections.emptyMap();
  }
}