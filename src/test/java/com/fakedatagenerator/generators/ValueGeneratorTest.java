package com.fakedatagenerator.generators;

import static org.junit.jupiter.api.Assertions.*;

import com.fakedatagenerator.FakeDataGeneratorConfiguration;
import com.fakedatagenerator.datatypes.DateDataType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = FakeDataGeneratorConfiguration.class)
@ExtendWith(SpringExtension.class)
class ValueGeneratorTest {

  @Autowired private ObjectMapper objectMapper;

  @Test
  public void deserialize_withValidInput_GivesValueGenerator() throws JsonProcessingException {
    String yamlString =
        """
                        type: method
                        provider: clashOfClans
                        method: troop
                    """;
    ValueGenerator valueGenerator = objectMapper.readValue(yamlString, ValueGenerator.class);
    System.out.println(valueGenerator.nextValue());
    Assertions.assertNotNull(valueGenerator.nextValue());
    Assertions.assertNotNull(valueGenerator);
  }

  @Test
  public void deserialize_withValidInputAndArguments_GivesValueGenerator()
      throws JsonProcessingException {
    String yamlString =
        """
                        type: method
                        provider: date
                        method: birthday
                        arguments:
                          - type: string
                            value: "yy DDD hh:mm:ss"
                    """;
    ValueGenerator valueGenerator = objectMapper.readValue(yamlString, ValueGenerator.class);
    Assertions.assertNotNull(valueGenerator.nextValue());
    Assertions.assertTrue(
        valueGenerator.nextValue().toString().matches("\\d{2} \\d{3} \\d{2}:\\d{2}:\\d{2}"));
    Assertions.assertNotNull(valueGenerator);
  }

  @Test
  public void deserialize_withNonStringArguments_CallsCorrectFunction()
      throws JsonProcessingException {
    String yamlString =
        """
                        type: method
                        provider: date
                        method: birthday
                        arguments:
                          - type: int
                            value: 0
                          - type: int
                            value: 18
                    """;
    ValueGenerator valueGenerator = objectMapper.readValue(yamlString, ValueGenerator.class);
    DateDataType dateDataType = new DateDataType();
    LocalDate ld = dateDataType.cast(valueGenerator.nextValue());
    Assertions.assertTrue(
        LocalDate.now().isAfter(ld) && LocalDate.now().minusYears(18).isBefore(ld));
    Assertions.assertNotNull(valueGenerator);
  }

  @Test
  public void deserialize_withNonStandardClassArguments_CallsCorrectFunction()
      throws JsonProcessingException {
    String yamlString =
        """
                            type: method
                            provider: number
                            method: digits
                            arguments:
                              - type: int
                                value: 10
                        """;
    ValueGenerator valueGenerator = objectMapper.readValue(yamlString, ValueGenerator.class);
    String number = (String) valueGenerator.nextValue();
    Assertions.assertEquals(10, number.length());
  }
}
