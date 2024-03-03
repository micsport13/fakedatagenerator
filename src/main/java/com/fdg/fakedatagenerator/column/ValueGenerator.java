package com.fdg.fakedatagenerator.column;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ValueGeneratorSerializer.class)
@JsonDeserialize(using = ValueGeneratorDeserializer.class)
public interface ValueGenerator {
  Object nextValue();
}
