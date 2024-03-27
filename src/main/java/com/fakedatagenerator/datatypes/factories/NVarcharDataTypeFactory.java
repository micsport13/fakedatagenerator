package com.fakedatagenerator.datatypes.factories;

import com.fakedatagenerator.datatypes.DataType;
import com.fakedatagenerator.datatypes.NVarcharDataType;
import com.fakedatagenerator.utils.Primitives;
import java.util.HashMap;
import java.util.Map;

public class NVarcharDataTypeFactory implements DataTypeFactory {

  @Override
  public DataType<?> createDataType(String dataType, Map<String, Object> args) {
    Integer maxLength =
        (Integer) args.getOrDefault("max_length", NVarcharDataType.DEFAULT_MAX_LENGTH);
    return new NVarcharDataType(maxLength);
  }

  @Override
  public Map<String, Primitives> getOptions() {
    Map<String, Primitives> options = new HashMap<>();
    options.put("max_length", Primitives.INT);
    return options;
  }
}
