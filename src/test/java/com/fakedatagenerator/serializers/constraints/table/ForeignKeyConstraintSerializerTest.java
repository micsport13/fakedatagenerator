package com.fakedatagenerator.serializers.constraints.table;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
class ForeignKeyConstraintSerializerTest {
  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {}
}
