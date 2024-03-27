package com.fakedatagenerator.datatypes.factories;

import com.fakedatagenerator.datatypes.DataType;
import com.fakedatagenerator.datatypes.FloatDataType;
import com.fakedatagenerator.utils.Primitives;
import java.util.Collections;
import java.util.Map;

public class FloatDataTypeFactory implements DataTypeFactory {

  @Override
  public DataType<?> createDataType(String dataType, Map<String, Object> args) {
    return new FloatDataType();
  }

  @Override
  public Map<String, Primitives> getOptions() {
    return Collections.emptyMap();
  }
}
