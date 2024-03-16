package com.fakedatagenerator.generators;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.lang.reflect.Field;

@JsonTest
class SequentialValueGeneratorTest {
  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {}

  @Test
  public void serialize_withValidInputs_outputsCorrectSequentialValueGenerator()
      throws JsonProcessingException {
    String expectedYaml = """
    type: sequence
    start: 1
    increment: 1
    """;
    SequentialValueGenerator sequentialValueGenerator = new SequentialValueGenerator(1, 1);
    String outputYaml = objectMapper.writeValueAsString(sequentialValueGenerator);
    assertEquals(expectedYaml, outputYaml);
  }

  @Test
  public void deserialize_withValidInputs_outputsCorrectSequentialValueGeneratorTest()
      throws JsonProcessingException {
    String inputYaml = """
    type: sequence
    start: 1
    increment: 1
    """;
    SequentialValueGenerator sequentialValueGenerator =
        objectMapper.readValue(inputYaml, SequentialValueGenerator.class);

    assertEquals(1, sequentialValueGenerator.getStartingValue());
    assertEquals(1, sequentialValueGenerator.getIncrement());
  }

  @Test
  public void nextValue_withManyIterations_givesCorrectNextValue() {
    SequentialValueGenerator sequentialValueGenerator = new SequentialValueGenerator(1, 1);
    for (int i = 0; i < 99999; i++) {
      sequentialValueGenerator.nextValue();
    }
    assertEquals(100000, sequentialValueGenerator.nextValue());
  }

  @Test
  public void nextValue_withManyIterationsAndNonStandardIncrement_givesCorrectNextValue() {
    SequentialValueGenerator sequentialValueGenerator = new SequentialValueGenerator(1, 1000);
    for (int i = 0; i < 100; i++) {
      sequentialValueGenerator.nextValue();
    }
    assertEquals(100001, sequentialValueGenerator.nextValue());
  }

  @Test
  public void nextValue_withNegativeIncrement_givesCorrectNextValue() {
    SequentialValueGenerator sequentialValueGenerator = new SequentialValueGenerator(-1, -1);
    for (int i = 0; i < 99999; i++) {
      sequentialValueGenerator.nextValue();
    }
    assertEquals(-100000, sequentialValueGenerator.nextValue());
  }

  @Test
  public void nextValue_withMaxIntegerValue_givesCorrectNextValue()
      throws NoSuchFieldException, IllegalAccessException {

    SequentialValueGenerator sequentialValueGenerator = new SequentialValueGenerator(1, 1);
    Field counterField = SequentialValueGenerator.class.getDeclaredField("counter");
    counterField.setAccessible(true);
    counterField.set(sequentialValueGenerator, Integer.MAX_VALUE);
    assertEquals(Integer.MIN_VALUE, sequentialValueGenerator.nextValue());
  }
}
