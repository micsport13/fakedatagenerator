package com.fakedatagenerator.datatypes.factories;

import com.fakedatagenerator.datatypes.DataType;
import com.fakedatagenerator.datatypes.VarcharDataType;
import com.fakedatagenerator.utils.Primitives;
import java.util.HashMap;
import java.util.Map;

public class VarcharDataTypeFactory implements DataTypeFactory {

  @Override
  public DataType<?> createDataType(String dataType, Map<String, Object> args) {
    Integer maxLength =
        (Integer) args.getOrDefault("max_length", VarcharDataType.DEFAULT_MAX_LENGTH);
    return new VarcharDataType(maxLength);
  }

  @Override
  public Map<String, Primitives> getOptions() {
    Map<String, Primitives> options = new HashMap<>();
    options.put("max_length", Primitives.INT);
    return options;
  }
}
