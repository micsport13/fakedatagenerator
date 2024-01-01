package com.fdg.fakedatagenerator.serializers.datatype;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
class DecimalDataTypeSerializerTest {

  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {}
}
