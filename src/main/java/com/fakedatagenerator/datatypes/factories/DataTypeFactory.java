package com.fakedatagenerator.datatypes.factories;

import com.fakedatagenerator.datatypes.*;
import com.fakedatagenerator.utils.FactoryOptions;
import java.util.*;

public interface DataTypeFactory extends FactoryOptions {

  DataType<?> createDataType(String dataType, Map<String, Object> args);
}
