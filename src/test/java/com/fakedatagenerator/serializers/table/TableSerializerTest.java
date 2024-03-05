package com.fakedatagenerator.serializers.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.constraints.NumericCheckConstraint;
import com.fakedatagenerator.datatypes.DecimalDataType;
import com.fakedatagenerator.datatypes.IntegerDataType;
import com.fakedatagenerator.datatypes.VarcharDataType;
import com.fakedatagenerator.schema.Schema;
import com.fakedatagenerator.table.Table;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
class TableSerializerTest {
  @Autowired private ObjectMapper objectMapper;

  @Test
  public void serialize_withValidTable_producesCorrectSerialization()
      throws JsonProcessingException {
    Column<IntegerDataType> idColumn = new Column<>("id", new IntegerDataType());
    Column<VarcharDataType> nameColumn = new Column<>("name", new VarcharDataType(40));
    Column<DecimalDataType> priceColumn = new Column<>("price", new DecimalDataType(18, 2));
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
       type:
         name: integer
     - name: name
       type:
         name: varchar
         max_length: 40
     - name: price
       type:
         name: decimal
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
}
