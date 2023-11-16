package com.fdg.fakedatagenerator.datatypes.factories;

import static org.junit.jupiter.api.Assertions.*;

import com.fdg.fakedatagenerator.datatypes.DecimalDataType;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.datatypes.VarcharDataType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class DataTypeFactoryTest {
  private final Map<String, Object> inputParameters = new HashMap<>();

  @Test
  public void create_withVarcharDataType_ReturnsVarcharDataType() {
    VarcharDataType varcharDataType =
        (VarcharDataType) DataTypeFactory.create("varchar", inputParameters);
    assertEquals(
        "VarcharDataType",
        VarcharDataType.class.getSimpleName(),
        varcharDataType.getClass().getSimpleName());
    assertEquals(1, varcharDataType.getMaxLength());
  }

  @Test
  public void create_withDecimalDataType_ReturnsConstructedDecimalDataType() {
    DecimalDataType decimalDataType =
        (DecimalDataType) DataTypeFactory.create("decimal", inputParameters);
    assertEquals(
        "DecimalDataType",
        DecimalDataType.class.getSimpleName(),
        decimalDataType.getClass().getSimpleName());
    assertEquals(18, decimalDataType.getPrecision());
    assertEquals(0, decimalDataType.getScale());
  }

  @Test
  public void create_withCorrectDecimalParameters_ReturnsCorrectDecimalDataType() {
    this.inputParameters.put("precision", 38);
    this.inputParameters.put("scale", 18);
    DecimalDataType decimalDataType =
        (DecimalDataType) DataTypeFactory.create("decimal", inputParameters);
    assertEquals(38, decimalDataType.getPrecision());
    assertEquals(18, decimalDataType.getScale());
  }

  @Test
  public void create_withNoPrecisionAndScaleProvided_ThrowsIllegalArgumentException() {
    this.inputParameters.put("scale", 18);
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          DataTypeFactory.create("decimal", inputParameters);
        });
  }

  @Test
  public void create_withScaleGreaterThanPrecision_ThrowsIllegalArgumentException() {
    this.inputParameters.put("precision", 10);
    this.inputParameters.put("scale", 12);
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          DataTypeFactory.create("decimal", inputParameters);
        });
  }

  @Test
  public void create_withIntegerDataType_ReturnsConstructedIntegerDataType() {
    IntegerDataType integerDataType =
        (IntegerDataType) DataTypeFactory.create("integer", inputParameters);
    assertEquals(
        "IntegerDataType",
        IntegerDataType.class.getSimpleName(),
        integerDataType.getClass().getSimpleName());
  }

  @Test
  public void create_VarcharDataTypeWithParameters_CreatesVarcharWithCorrectLength() {
    this.inputParameters.put("maxLength", 10);
    VarcharDataType varcharDataType =
        (VarcharDataType) DataTypeFactory.create("varchar", inputParameters);
    assertEquals(10, varcharDataType.getMaxLength());
  }

  @Test
  public void create_VarcharDataTypeFromString_CreatesVarcharDataType() {
    assertEquals(
        VarcharDataType.class, DataTypeFactory.create("varchar", inputParameters).getClass());
    assertEquals(
        VarcharDataType.class, DataTypeFactory.create("VARCHAR", inputParameters).getClass());
    assertEquals(
        VarcharDataType.class, DataTypeFactory.create("Varchar", inputParameters).getClass());
  }

  @Test
  public void create_DecimalDataTypeFromString_CreatesDecimalDatatype() {
    assertEquals(
        DecimalDataType.class, DataTypeFactory.create("decimal", inputParameters).getClass());
    assertEquals(
        DecimalDataType.class, DataTypeFactory.create("DECIMAL", inputParameters).getClass());
    assertEquals(
        DecimalDataType.class, DataTypeFactory.create("Decimal", inputParameters).getClass());
  }

  @Test
  public void create_IntegerDataTypeFromString_CreatesIntegerDatatype() {
    assertEquals(IntegerDataType.class, DataTypeFactory.create("INT", inputParameters).getClass());
    assertEquals(
        IntegerDataType.class, DataTypeFactory.create("Integer", inputParameters).getClass());
    assertEquals(IntegerDataType.class, DataTypeFactory.create("int", inputParameters).getClass());
  }
}
