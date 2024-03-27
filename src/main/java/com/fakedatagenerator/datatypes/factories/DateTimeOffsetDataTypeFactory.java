package com.fakedatagenerator.datatypes.factories;

import com.fakedatagenerator.datatypes.DataType;
import com.fakedatagenerator.datatypes.DateTimeOffsetDataType;
import com.fakedatagenerator.utils.Primitives;
import java.time.DateTimeException;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DateTimeOffsetDataTypeFactory implements DataTypeFactory {

  @Override
  public DataType<?> createDataType(String dataType, Map<String, Object> args) {
    ZoneOffset zoneOffset;
    try {
      zoneOffset = ZoneOffset.of((String) args.get("zone_offset"));
    } catch (DateTimeException e) {
      log.warn("Unable to find zone_offset id, defaulting to UTC");
      zoneOffset = ZoneOffset.of("UTC");
    }
    return new DateTimeOffsetDataType(zoneOffset);
  }

  @Override
  public Map<String, Primitives> getOptions() {
    Map<String, Primitives> options = new HashMap<>();
    options.put("zone_offset", Primitives.STRING);
    return options;
  }
}
