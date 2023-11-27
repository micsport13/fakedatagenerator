package com.fdg.fakedatagenerator.datatypes.factories;

import com.fdg.fakedatagenerator.datatypes.DataType;
import com.fdg.fakedatagenerator.datatypes.DecimalDataType;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.datatypes.VarcharDataType;
import java.util.Map;

public class DataTypeFactory {
  public static DataType<?> create(String dataType, Map<String, String> parameters) {
    switch (dataType.toLowerCase()) {
      case "varchar":
        if (parameters.get("max_length") != null) {
          int maxLength = Integer.parseInt(parameters.get("max_length"));
          return new VarcharDataType(maxLength);
        } else {
          return new VarcharDataType();
        }
      case "decimal":
        int precision = 18;
        int scale = 0;
        if (parameters.get("scale") != null && parameters.get("precision") == null) {
          throw new IllegalArgumentException("If scale is given, precision must also be provided.");
        }
        if (parameters.get("scale") != null && parameters.get("precision") != null) {
          scale = Integer.parseInt(parameters.get("scale"));
          precision = Integer.parseInt(parameters.get("precision"));
        }
        if (parameters.get("precision") != null) {
          precision = Integer.parseInt(parameters.get("precision"));
        }
        return new DecimalDataType(precision, scale);
      case "integer", "int":
        return new IntegerDataType();
      default:
        throw new IllegalArgumentException("Unknown data type: " + dataType);
    }
  }
}
