package com.fakedatagenerator.utils;

public enum Primitives {
  INT(Integer.class, "int"),
  STRING(String.class, "string"),
  FLOAT(Float.class, "float"),
  BOOLEAN(Boolean.class, "boolean"),
  DOUBLE(Double.class, "double"),
  LONG(Long.class, "long"),
  BYTE(Byte.class, "byte"),
  CHAR(Character.class, "char"),
  SHORT(Short.class, "short"),
  STRING_ARRAY(String[].class, "string_array"),
  INT_ARRAY(Integer[].class, "int_array"),
  OBJECT_ARRAY(Object[].class, "object_array");

  private final Class<?> clazz;
  private final String name;

  Primitives(Class<?> clazz, String name) {
    this.clazz = clazz;
    this.name = name;
  }

  public static Primitives fromString(String name) {
    for (Primitives primitive : Primitives.values()) {
      if (primitive.name.equals(name)) {
        return primitive;
      }
    }
    throw new IllegalArgumentException("Invalid primitive type");
  }

  public static String[] getAliases() {
    String[] aliases = new String[Primitives.values().length];
    int i = 0;
    for (Primitives primitive : Primitives.values()) {
      aliases[i] = primitive.name;
      i++;
    }
    return aliases;
  }
}
