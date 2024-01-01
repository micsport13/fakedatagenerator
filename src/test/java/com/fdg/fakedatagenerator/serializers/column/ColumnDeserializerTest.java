package com.fdg.fakedatagenerator.serializers.column;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.column.ValueGenerator;
import com.fdg.fakedatagenerator.constraints.column.ColumnCheckConstraint;
import com.fdg.fakedatagenerator.constraints.column.ColumnConstraint;
import com.fdg.fakedatagenerator.constraints.column.NotNullConstraint;
import com.fdg.fakedatagenerator.datatypes.DecimalDataType;
import com.fdg.fakedatagenerator.datatypes.VarcharDataType;
import java.lang.reflect.InvocationTargetException;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
class ColumnDeserializerTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void deserialize_GivenInputString_DeserializesToDecimalColumn() {
    String yamlString =
        """
                        name: decColumn
                        type:
                          name: decimal
                          precision: 38
                          scale: 20
                        """;

    try {
      Column<?> column = objectMapper.readValue(yamlString, Column.class);
      Column<DecimalDataType> decColumn = new Column<>("decColumn", new DecimalDataType(38, 20));
      assertEquals(decColumn, column);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void deserialize_GivenInputString_DeserializesToVarcharColumn() {
    String yamlString =
        """
                        name: "varcharColumn"
                        type:
                          name: "varchar"
                          max_length: 40
                        """;

    try {
      Column<?> column = objectMapper.readValue(yamlString, Column.class);
      Column<VarcharDataType> decColumn = new Column<>("varcharColumn", new VarcharDataType(40));
      assertEquals(decColumn, column);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void deserialize_WithConstraints_DeserializesWithoutErrors() {
    String yamlString =
        """
            name: testCol
            type:
              name: varchar
              maxLength: 40
            constraints:
              - type: not_null""";
    try {
      Column<?> column = objectMapper.readValue(yamlString, Column.class);
      Column<VarcharDataType> decColumn =
          new Column<>("testCol", new VarcharDataType(40), new NotNullConstraint());
      assertEquals(decColumn, column);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void deserialize_WithCheckConstraint_DeserializesWithoutErrors() {
    String yamlString =
        """
            name: testCol
            type:
              name: decimal
              precision: 38
              scale: 20
            constraints:
              - type: check_constraint
                min_value: 0
                max_value: 10""";
    try {
      Column<?> column = objectMapper.readValue(yamlString, Column.class);
      ColumnConstraint checkConstraint =
          new ColumnCheckConstraint.Builder().withRange(0, 10).build();
      Column<DecimalDataType> decColumn =
          new Column<>("testCol", new DecimalDataType(38, 20), checkConstraint);
      assertEquals(decColumn, column);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void deserializeColumn_withGenerator_givesValidOutput() {
    String yamlString =
        """
                name: testCol
                type:
                  name: varchar
                  max_length: 40
                generator:
                  package: clashofclans
                  method: troop""";
    try {
      InjectableValues.Std inject = new InjectableValues.Std();
      inject.addValue(Faker.class, new Faker());
      objectMapper.setInjectableValues(
          inject); // TODO: Figure out how to auto inject this everytime
      Column<?> column = objectMapper.readValue(yamlString, Column.class);
      Column<VarcharDataType> decColumn =
          new Column<>(
              "testCol",
              new VarcharDataType(40),
              new ValueGenerator(new Faker(), "clashofclans", "troop"));
      assertEquals(decColumn, column);
    } catch (JsonProcessingException | InvocationTargetException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
