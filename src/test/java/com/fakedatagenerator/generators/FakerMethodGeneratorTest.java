package com.fakedatagenerator.generators;

import static org.junit.jupiter.api.Assertions.*;

import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
class FakerMethodGeneratorTest {
  @Autowired private Faker faker;

  @BeforeEach
  void setUp() {}

  @Test
  public void get_withValidInput_GivesValue() {
    ValueGenerator valueGenerator = new FakerMethodGenerator(faker, "clashofclans", "troop");
    assertNotNull(valueGenerator.nextValue());
  }

  @Test
  public void get_withNonValidExpression_ThrowsRuntimeException() {
    ValueGenerator valueGenerator = new FakerMethodGenerator(faker, "clashofclans", "random");
    assertThrows(RuntimeException.class, valueGenerator::nextValue);
  }

  @Test
  public void get_withMethodArguments_GivesValueWithArguments() {
    ValueGenerator valueGenerator =
        new FakerMethodGenerator(faker, "date", "birthday", "yy DDD hh:mm:ss");
    assertTrue(valueGenerator.nextValue().toString().matches("\\d{2} \\d{3} \\d{2}:\\d{2}:\\d{2}"));
  }
}
