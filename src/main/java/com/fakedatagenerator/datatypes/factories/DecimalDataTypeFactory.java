package com.fakedatagenerator.datatypes.factories;

import com.fakedatagenerator.datatypes.DataType;
import com.fakedatagenerator.datatypes.DecimalDataType;
import com.fakedatagenerator.utils.Primitives;
import java.util.HashMap;
import java.util.Map;

public class DecimalDataTypeFactory implements DataTypeFactory {
  @Override
  public DataType<?> createDataType(String dataType, Map<String, Object> args) {
    Integer precision = (Integer) args.getOrDefault("precision", DecimalDataType.DEFAULT_PRECISION);
    Integer scale = (Integer) args.getOrDefault("scale", DecimalDataType.DEFAULT_SCALE);
    return new DecimalDataType(precision, scale);
  }

  @Override
  public Map<String, Primitives> getOptions() {
    Map<String, Primitives> options = new HashMap<>();
    options.put("precision", Primitives.INT);
    options.put("scale", Primitives.INT);
    return options;
  }
}
