package com.fakedatagenerator.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.constraints.NumericCheckConstraint;
import com.fakedatagenerator.constraints.UniqueConstraint;
import com.fakedatagenerator.datatypes.DecimalDataType;
import com.fakedatagenerator.datatypes.IntegerDataType;
import com.fakedatagenerator.datatypes.VarcharDataType;
import com.fakedatagenerator.exceptions.UniqueConstraintException;
import com.fakedatagenerator.row.Row;
import com.fakedatagenerator.schema.Schema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

/** The type Table test. */
@JsonTest
class TableTest {
  /*
  TODO: Testing validations
      Check state (Correct columns, correct multi constraints, name of multi)
      Table name validation
      Check adding row to make sure correct error is thrown.
      Check schema definition and schema order
   */
  @Autowired private ObjectMapper objectMapper;
  private Table table;
  private Row testRow;

  /** Sets up. */
  @BeforeEach
  public void setUp() {
    Schema schema =
        new Schema(
            new Column("id", new IntegerDataType()), new Column("name", new VarcharDataType(10)));
    this.table = new Table("TableTest", schema);
    this.testRow =
        new Row.Builder(
                new Column("id", new IntegerDataType()),
                new Column("name", new VarcharDataType(10)))
            .withColumnValue("id", 1)
            .withColumnValue("name", "Dave")
            .build();
  }

  /** Add member throws no exception. */
  @Test
  public void add_WithValidEntity_SuccessfullyAddedToTable() {
    this.testRow.setColumnValue("id", 1);
    this.table.add(testRow);
  }

  /** Add multiple members throws no exception. */
  @Test
  public void add_WithMultipleEntities_AllAddedSucessfullyToTable() {
    this.table.add(this.testRow);
    this.table.add(this.testRow);
    assertEquals(2, this.table.getEntities().size());
  }

  /** Add member with unique.yml single throws no exception. */
  @Test
  public void addTableConstraint_WithUniqueColumn_ThrowsNoException() {
    Column column = this.testRow.getColumnByName("name");
    this.table.addConstraint(new UniqueConstraint(), column);
    this.table.add(this.testRow);
    assertEquals(1, this.table.getEntities().size());
  }

  /** Add multiple members with unique.yml values throws no exception. */
  @Test
  public void add_MultipleEntitiesOnUniqueColumn_ThrowsNoException() {
    Row testRow2 =
        new Row.Builder(
                new Column("id", new IntegerDataType()),
                new Column("name", new VarcharDataType(10)))
            .withColumnValue("id", 2)
            .withColumnValue("name", "John")
            .build();
    Column column = this.testRow.getColumnByName("name");
    this.table.addConstraint(new UniqueConstraint(), column);
    this.table.add(testRow);
    Assertions.assertDoesNotThrow(() -> this.table.add(testRow2));
  }

  /** Add multiple members with non unique.yml values throws exception. */
  @Test
  public void add_MultipleMembersWithNonUniqueIntoUniqueColumn_ValuesThrowsException() {
    Column column = this.testRow.getColumnByName("name");
    this.table.addConstraint(new UniqueConstraint(), column);
    this.table.add(this.testRow);
    Assertions.assertThrows(UniqueConstraintException.class, () -> this.table.add(testRow));
  }

  @Test
  public void serialize_withValidTable_producesCorrectSerialization()
      throws JsonProcessingException {
    Column idColumn = new Column("id", new IntegerDataType());
    Column nameColumn = new Column("name", new VarcharDataType(40));
    Column priceColumn = new Column("price", new DecimalDataType(18, 2));
    Schema schema = new Schema(idColumn, nameColumn, priceColumn);
    schema.addConstraint(
        new NumericCheckConstraint.Builder<Double>().withMinimumValue(0.0).build(), priceColumn);
    Table table = new Table("test", schema);
    String expectedYaml =
        """
     name: test
     schema:
       columns:
         - name: id
           data_type:
             type: integer
         - name: name
           data_type:
             type: varchar
             max_length: 40
         - name: price
           data_type:
             type: decimal
             precision: 18
             scale: 2
       constraints:
         - constraint:
             type: numeric_check
             minValue: 0.0
           columns:
             - price
     """;
    assertEquals(expectedYaml, objectMapper.writeValueAsString(table));
  }

  @Test
  public void deserialize_withValidInput_returnsCorrectTableObject()
      throws JsonProcessingException {
    String testYaml =
        """
    name: test
    schema:
      columns:
        - name: id
          data_type:
            type: integer
        - name: first_name
          data_type:
            type: varchar
            max_length: 50
        - name: last_name
          data_type:
            type: varchar
            max_length: 50
      constraints:
        - constraint:
            type: primary_key
          columns:
            - id
        - constraint:
            type: unique
          columns:
            - first_name
            - last_name""";
    Table table = objectMapper.readValue(testYaml, Table.class);
    Assertions.assertEquals("test", table.getName());
    Assertions.assertEquals(3, table.getSchema().getColumns().size());
  }
}
