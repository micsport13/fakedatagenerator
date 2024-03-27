package com.fakedatagenerator.datatypes.factories;

import com.fakedatagenerator.datatypes.DataType;
import com.fakedatagenerator.utils.Primitives;
import java.util.Map;

public class TextDataTypeFactory implements DataTypeFactory {

  @Override
  public DataType<?> createDataType(String dataType, Map<String, Object> args) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Map<String, Primitives> getOptions() {
    throw new UnsupportedOperationException();
  }
}
