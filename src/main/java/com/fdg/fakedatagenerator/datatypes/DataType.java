package com.fdg.fakedatagenerator.datatypes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fdg.fakedatagenerator.exceptions.DeserializationException;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, property = "name")
@JsonSubTypes({
  @JsonSubTypes.Type(value = BigIntDataType.class, name = "bigint"),
  @JsonSubTypes.Type(value = BinaryDataType.class, name = "binary"),
  @JsonSubTypes.Type(
      value = BitDataType.class,
      names = {"bit", "boolean"}),
  @JsonSubTypes.Type(value = CharDataType.class, name = "char"),
  @JsonSubTypes.Type(value = DateDataType.class, name = "date"),
  @JsonSubTypes.Type(value = DateTime2DataType.class, name = "datetime2"),
  @JsonSubTypes.Type(value = DateTimeOffsetDataType.class, name = "datetimeoffset"),
  @JsonSubTypes.Type(value = DecimalDataType.class, name = "decimal"),
  @JsonSubTypes.Type(value = FloatDataType.class, name = "float"),
  @JsonSubTypes.Type(
      value = IntegerDataType.class,
      names = {"integer", "int"}),
  @JsonSubTypes.Type(value = MoneyDataType.class, name = "money"),
  @JsonSubTypes.Type(value = NVarcharDataType.class, name = "nvarchar"),
  @JsonSubTypes.Type(value = RealDataType.class, name = "real"),
  @JsonSubTypes.Type(value = SmallIntDataType.class, name = "smallint"),
  @JsonSubTypes.Type(value = SmallMoneyDataType.class, name = "smallmoney"),
  @JsonSubTypes.Type(value = TextDataType.class, name = "text"),
  @JsonSubTypes.Type(value = TimeDataType.class, name = "time"),
  @JsonSubTypes.Type(value = TinyIntDataType.class, name = "tinyint"),
  @JsonSubTypes.Type(value = UniqueIdentifierDataType.class, name = "uniqueidentifier"),
  @JsonSubTypes.Type(value = VarcharDataType.class, name = "varchar"),
})
public interface DataType<T> {
  Object store(Object value) throws MismatchedDataTypeException;

  T cast(Object value) throws DeserializationException;

  boolean validate(String value);
}
