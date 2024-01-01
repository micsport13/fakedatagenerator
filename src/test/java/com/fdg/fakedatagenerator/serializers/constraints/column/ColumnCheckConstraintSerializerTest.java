package com.fdg.fakedatagenerator.serializers.constraints.column;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
class ColumnCheckConstraintSerializerTest {
  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {}
}
