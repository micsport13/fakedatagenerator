package com.fakedatagenerator.datatypes.factories;

import com.fakedatagenerator.datatypes.CharDataType;
import com.fakedatagenerator.datatypes.DataType;
import com.fakedatagenerator.utils.Primitives;
import java.util.HashMap;
import java.util.Map;

public class CharDataTypeFactory implements DataTypeFactory {

  @Override
  public DataType<?> createDataType(String dataType, Map<String, Object> args) {
    Integer maxLength = (Integer) args.get("max_length");
    return new CharDataType(maxLength);
  }

  @Override
  public Map<String, Primitives> getOptions() {
    Map<String, Primitives> options = new HashMap<>();
    options.put("max_length", Primitives.INT);
    return options;
  }
}
