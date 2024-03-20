package com.fakedatagenerator.datatypes.factories;

import com.fakedatagenerator.datatypes.DataType;

public interface DataTypeFactory<T extends DataType<?>> {
  T create(Object... args);
}
