package com.fdg.fakedatagenerator.datatypes.factories;

import com.fdg.fakedatagenerator.datatypes.DataType;
import com.fdg.fakedatagenerator.datatypes.DecimalDataType;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.datatypes.VarcharDataType;

import java.util.Map;

public class DataTypeFactory {
    public static DataType<?> create(String dataType, Map<String, Object> parameters) {
        switch (dataType.toLowerCase()) {
            case "varchar":
                int maxLength = (int) parameters.get("maxLength");
                return new VarcharDataType(maxLength);
            case "decimal":
                int scale = (int) parameters.get("scale");
                int precision = (int) parameters.get("precision");
                return new DecimalDataType(scale, precision);
            case "integer","int":
                return new IntegerDataType();
            default:
                throw new IllegalArgumentException("Unknown data type: " + dataType);
        }
    }
}