package com.fdg.fakedatagenerator.serializers.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.multi.PrimaryKeyConstraint;
import com.fdg.fakedatagenerator.constraints.multi.UniqueLevelConstraint;
import com.fdg.fakedatagenerator.constraints.single.NumericCheckConstraint;
import com.fdg.fakedatagenerator.datatypes.DecimalDataType;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.datatypes.VarcharDataType;
import com.fdg.fakedatagenerator.schema.Schema;
import com.fdg.fakedatagenerator.table.Table;
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
    schema.addConstraint(new PrimaryKeyConstraint(), idColumn);
    schema.addConstraint(new UniqueLevelConstraint(), nameColumn);
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
       - type: numeric_check
         minValue: 0.0
         column: price
 """;
    assertEquals(expectedYaml, objectMapper.writeValueAsString(table));
  }
}
