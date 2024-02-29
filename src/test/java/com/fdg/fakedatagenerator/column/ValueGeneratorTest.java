package com.fdg.fakedatagenerator.column;

import static com.fdg.fakedatagenerator.column.ValueGenerator.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fdg.fakedatagenerator.FakeDataGeneratorConfiguration;
import javax.swing.*;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = FakeDataGeneratorConfiguration.class)
@ExtendWith(SpringExtension.class)
class ValueGeneratorTest {

  @Autowired private Faker faker;

  @Test
  public void get_withValidInput_GivesValue() {
    ValueGenerator valueGenerator = new ValueGenerator(faker, "clashofclans", "troop");
    assertNotNull(valueGenerator.get());
  }

  @Test
  public void get_withNonValidExpression_ThrowsRuntimeException() {
    ValueGenerator valueGenerator = new ValueGenerator(faker, "clashofclans", "random");
    assertThrows(RuntimeException.class, valueGenerator::get);
  }

  @Test
  public void get_withMethodArguments_GivesValueWithArguments() {
    ValueGenerator valueGenerator =
        new ValueGenerator(faker, "date", "birthday", "yy DDD hh:mm:ss");
    assertTrue(valueGenerator.get().toString().matches("\\d{2} \\d{3} \\d{2}:\\d{2}:\\d{2}"));
  }
}
