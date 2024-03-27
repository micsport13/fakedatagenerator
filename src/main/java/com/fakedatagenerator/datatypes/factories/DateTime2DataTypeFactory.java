package com.fakedatagenerator.datatypes.factories;

import com.fakedatagenerator.datatypes.DataType;
import com.fakedatagenerator.datatypes.DateTime2DataType;
import com.fakedatagenerator.utils.Primitives;
import java.time.DateTimeException;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DateTime2DataTypeFactory implements DataTypeFactory {

  @Override
  public DataType<?> createDataType(String dataType, Map<String, Object> args) {
    ZoneOffset zoneOffset;
    try {
      zoneOffset = ZoneOffset.of((String) args.get("zone_offset"));
    } catch (DateTimeException e) {
      log.warn("Unable to find zone_offset id, defaulting to UTC");
      zoneOffset = ZoneOffset.UTC;
    }
    return new DateTime2DataType(zoneOffset);
  }

  @Override
  public Map<String, Primitives> getOptions() {
    Map<String, Primitives> options = new HashMap<>();
    options.put("zone_offset", Primitives.STRING);
    return options;
  }
}
