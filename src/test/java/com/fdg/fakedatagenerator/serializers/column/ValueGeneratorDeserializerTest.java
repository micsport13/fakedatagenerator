package com.fdg.fakedatagenerator.serializers.column;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdg.fakedatagenerator.FakeDataGeneratorConfiguration;
import com.fdg.fakedatagenerator.column.ValueGenerator;
import com.fdg.fakedatagenerator.datatypes.DateDataType;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = FakeDataGeneratorConfiguration.class)
public class ValueGeneratorDeserializerTest {
  @Autowired private ObjectMapper objectMapper;

  @Test
  public void deserialize_withValidInput_GivesValueGenerator() {
    String yamlString =
        """
                provider: clashOfClans
                method: troop
            """;
    try {
      ValueGenerator valueGenerator = objectMapper.readValue(yamlString, ValueGenerator.class);
      System.out.println(valueGenerator.get());
      Assertions.assertNotNull(valueGenerator.get());
      Assertions.assertNotNull(valueGenerator);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void deserialize_withValidInputAndArguments_GivesValueGenerator() {
    String yamlString =
        """
                provider: date
                method: birthday
                arguments:
                  - "yy DDD hh:mm:ss"
            """;
    try {
      ValueGenerator valueGenerator = objectMapper.readValue(yamlString, ValueGenerator.class);
      Assertions.assertNotNull(valueGenerator.get());
      Assertions.assertTrue(
          valueGenerator.get().toString().matches("\\d{2} \\d{3} \\d{2}:\\d{2}:\\d{2}"));
      Assertions.assertNotNull(valueGenerator);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void deserialize_withNonStringArguments_CallsCorrectFunction() {
    String yamlString =
        """
                provider: date
                method: birthday
                arguments:
                  - 0
                  - 18
            """;
    try {
      ValueGenerator valueGenerator = objectMapper.readValue(yamlString, ValueGenerator.class);
      DateDataType dateDataType = new DateDataType();
      LocalDate ld = dateDataType.cast(valueGenerator.get());
      Assertions.assertTrue(
          LocalDate.now().isAfter(ld) && LocalDate.now().minusYears(18).isBefore(ld));
      Assertions.assertNotNull(valueGenerator);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void deserialize_withNonStandardClassArguments_CallsCorrectFunction() {
    String yamlString =
        """
                    provider: number
                    method: digits
                    arguments:
                      - 10
                """;
    try {
      ValueGenerator valueGenerator = objectMapper.readValue(yamlString, ValueGenerator.class);
      String number = (String) valueGenerator.get();
      Assertions.assertEquals(10, Long.valueOf(number).toString().length());
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
