package com.fdg.fakedatagenerator.serializers.table;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
class TableDeserializerTest {
  @Autowired private ObjectMapper objectMapper;
}
