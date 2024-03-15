package com.fakedatagenerator.column;

import static org.junit.jupiter.api.Assertions.*;

import com.fakedatagenerator.datatypes.DecimalDataType;
import com.fakedatagenerator.datatypes.IntegerDataType;
import com.fakedatagenerator.datatypes.VarcharDataType;
import com.fakedatagenerator.exceptions.MismatchedDataTypeException;
import com.fakedatagenerator.generators.FakerMethodGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

/** The type Column test. */
@JsonTest
public class ColumnTest {
  @Autowired private ObjectMapper objectMapper;
  @Autowired private Faker faker;

  /** The Int single. */
  public Column intColumn;

  /** Sets . */
  @BeforeEach
  public void setup() {
    intColumn = new Column("int", new IntegerDataType());
  }

  // Constructors
  @Test
  public void constructor_WithValidNameAndDataType_SetsNameAndDataType() {
    assertEquals("int", intColumn.getName());
    assertEquals(IntegerDataType.class, intColumn.getDataType().getClass());
  }

  @Test
  public void constructor_WithValidNameDataTypeAndConstraints_SetsNameDataTypeAndConstraints() {
    Column column = new Column("int", new IntegerDataType());
    assertEquals("int", column.getName());
    assertEquals(IntegerDataType.class, column.getDataType().getClass());
  }

  // Add constraints

  // Equals
  @Test
  public void equals_WithAnotherColumnOfSameNameAndType_ColumnsAreEqual() {
    Column column = new Column("int", new IntegerDataType());
    assertEquals(column.getDataType().hashCode(), intColumn.getDataType().hashCode());
    assertEquals(intColumn, column);
  }

  // IsValid
  @Test
  public void isValid_WithValidValue_ReturnsTrue() {
    assertDoesNotThrow(() -> intColumn.validate(1));
  }

  @Test
  public void isValid_WithNullValueInNullableColumn_ReturnsTrue() {
    Column column = new Column("int", new IntegerDataType());
    assertDoesNotThrow(() -> column.validate(null));
  }

  @Test
  public void isValid_WithInvalidValue_ReturnsFalse() {
    assertThrows(MismatchedDataTypeException.class, () -> this.intColumn.validate("test"));
  }

  @Test
  public void deserialize_GivenInputString_DeserializesToDecimalColumn()
      throws JsonProcessingException {
    String yamlString =
        """
                            name: decColumn
                            data_type:
                              type: decimal
                              precision: 38
                              scale: 20
                            """;

    Column column = objectMapper.readValue(yamlString, Column.class);
    Column decColumn = new Column("decColumn", new DecimalDataType(38, 20));
    assertEquals(decColumn, column);
  }

  @Test
  public void deserialize_GivenInputString_DeserializesToVarcharColumn()
      throws JsonProcessingException {
    String yamlString =
        """
                            name: "varcharColumn"
                            data_type:
                              type: varchar
                              max_length: 40
                            """;
    Column column = objectMapper.readValue(yamlString, Column.class);
    Column decColumn = new Column("varcharColumn", new VarcharDataType(40));
    assertEquals(decColumn, column);
  }

  @Test
  public void deserialize_WithConstraints_DeserializesWithoutErrors()
      throws JsonProcessingException {
    String yamlString =
        """
                name: testCol
                data_type:
                  type: varchar
                  maxLength: 40""";

    Column column = objectMapper.readValue(yamlString, Column.class);
    Column decColumn = new Column("testCol", new VarcharDataType(40));
    assertEquals(decColumn, column);
  }

  @Test
  public void deserialize_WithCheckConstraint_DeserializesWithoutErrors()
      throws JsonProcessingException {
    String yamlString =
        """
                name: testCol
                data_type:
                  type: decimal
                  precision: 38
                  scale: 20""";
    Column column = objectMapper.readValue(yamlString, Column.class);
    Column decColumn = new Column("testCol", new DecimalDataType(38, 20));
    assertEquals(decColumn, column);
  }

  @Test
  public void deserializeColumn_withGenerator_givesValidOutput() throws JsonProcessingException {
    String yamlString =
        """
                    name: testCol
                    data_type:
                      type: varchar
                      max_length: 40
                    generator:
                      type: method
                      provider: clashofclans
                      method: troop""";
    Column column = objectMapper.readValue(yamlString, Column.class);
    Column decColumn =
        new Column(
            "testCol",
            new VarcharDataType(40),
            new FakerMethodGenerator(faker, "clashofclans", "troop"));
    assertEquals(decColumn, column);
  }

  @Test
  public void serialize_ColumnSerialization_OutputsCorrectSerialization() throws IOException {
    Column intColumn = new Column("intColumn", new IntegerDataType());
    String yaml = objectMapper.writeValueAsString(intColumn);
    // Assert
    String expectedYaml =
        """
                name: intColumn
                data_type:
                  type: integer
                """;
    assertEquals(expectedYaml, yaml);
  }

  @Test
  public void serialize_DecimalDataTypeColumnWithNotNullConstraint_OutputsCorrectSerialization()
      throws IOException {
    Column decColumn = new Column("decColumn", new DecimalDataType(38, 20));

    String yaml = objectMapper.writeValueAsString(decColumn);
    // Assert
    String expectedYaml =
        """
                    name: decColumn
                    data_type:
                      type: decimal
                      precision: 38
                      scale: 20
                    """;
    assertEquals(expectedYaml, yaml);
  }

  @Test
  public void serialize_VarcharDataTypeWithNotNullConstraints_OutputsCorrectSerialization()
      throws IOException {
    Column varcharDataTypeColumn = new Column("varcharColumn", new VarcharDataType(40));
    String yaml = objectMapper.writeValueAsString(varcharDataTypeColumn);
    // Assert
    String expectedYaml =
        """
                        name: varcharColumn
                        data_type:
                          type: varchar
                          max_length: 40
                        """;
    assertEquals(expectedYaml, yaml);
  }

  @Test
  public void serialize_IntegerDataTypeWithNotNullConstraints_OutputsCorrectSerialization()
      throws IOException {
    Column varcharDataTypeColumn = new Column("integerColumn", new IntegerDataType());
    String yaml = objectMapper.writeValueAsString(varcharDataTypeColumn);
    // Assert
    String expectedYaml =
        """
                        name: integerColumn
                        data_type:
                          type: integer
                        """;
    assertEquals(expectedYaml, yaml);
  }
}
