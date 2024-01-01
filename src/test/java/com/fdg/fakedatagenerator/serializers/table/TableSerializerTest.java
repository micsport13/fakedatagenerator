package com.fdg.fakedatagenerator.serializers.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.column.ColumnCheckConstraint;
import com.fdg.fakedatagenerator.constraints.table.PrimaryKeyConstraint;
import com.fdg.fakedatagenerator.constraints.table.UniqueConstraint;
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
    Column<DecimalDataType> priceColumn =
        new Column<>(
            "price",
            new DecimalDataType(18, 2),
            new ColumnCheckConstraint.Builder().withMinimumValue(0).build());
    Schema schema = new Schema(idColumn, nameColumn, priceColumn);
    schema.addConstraint(idColumn, new PrimaryKeyConstraint());
    schema.addConstraint(nameColumn, new UniqueConstraint());
    Table table = new Table("test", schema);
    String expectedYaml =
        """
 name: test
 schema:
   columns:
     - column:
         name: id
         type:
           name: integer
       table_constraints:
         - type: primary_key
     - column:
         name: name
         type:
           name: varchar
           max_length: 40
       table_constraints:
         - type: unique
     - column:
         name: price
         type:
           name: decimal
           precision: 18
           scale: 2
         constraints:
           - type: check_constraint
             minValue: 0
 """;
    assertEquals(expectedYaml, objectMapper.writeValueAsString(table));
  }
}
