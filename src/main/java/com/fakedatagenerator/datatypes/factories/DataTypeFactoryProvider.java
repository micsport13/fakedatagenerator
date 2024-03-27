package com.fakedatagenerator.datatypes.factories;

import com.fakedatagenerator.datatypes.*;

public class DataTypeFactoryProvider {
  public static DataTypeFactory getDataTypeFactory(String dataType) {
    return switch (dataType.toLowerCase()) {
      case "bigint" -> new BigIntDataTypeFactory();
      case "binary" -> new BinaryDataTypeFactory();
      case "bit", "bool", "boolean" -> new BitDataTypeFactory();
      case "char" -> new CharDataTypeFactory();
      case "datetime", "datetime2" -> new DateTime2DataTypeFactory();
      case "datetimeoffset" -> new DateTimeOffsetDataTypeFactory();
      case "decimal", "numeric" -> new DecimalDataTypeFactory();
      case "float" -> new FloatDataTypeFactory();
      case "int", "integer" -> new IntegerDataTypeFactory();
      case "nvarchar" -> new NVarcharDataTypeFactory();
      case "real" -> new RealDataTypeFactory();
      case "smallint" -> new SmallIntDataTypeFactory();
      case "text" -> new TextDataTypeFactory();
      case "time" -> new TimeDataTypeFactory();
      case "tinyint" -> new TinyIntDataTypeFactory();
      case "uniqueidentifier" -> new UniqueIdentifierDataTypeFactory();
      case "varchar" -> new VarcharDataTypeFactory();
      default -> throw new IllegalArgumentException("Unable to locate data type factory");
    };
  }
}
