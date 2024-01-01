package com.fdg.fakedatagenerator.serializers.schema;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.constraints.table.PrimaryKeyConstraint;
import com.fdg.fakedatagenerator.constraints.table.UniqueConstraint;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.datatypes.VarcharDataType;
import com.fdg.fakedatagenerator.schema.Schema;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
class SchemaSerializerTest {

  @Autowired private ObjectMapper objectMapper;

  @Test
  public void serialize_Schema_OutputsCorrectSerialization() throws IOException {
    Column<IntegerDataType> intColumn = new Column<>("intColumn", new IntegerDataType());
    Column<VarcharDataType> varcharColumn =
        new Column<VarcharDataType>("varcharColumn", new VarcharDataType());
    Schema schema = new Schema(intColumn, varcharColumn);
    schema.addConstraint(intColumn, new PrimaryKeyConstraint());
    schema.addConstraint(varcharColumn, new UniqueConstraint());
    String yaml = objectMapper.writeValueAsString(schema);
    // Assert
    String expectedYaml =
        """
                    columns:
                      - column:
                          name: intColumn
                          type:
                            name: integer
                        table_constraints:
                          - type: primary_key
                      - column:
                          name: varcharColumn
                          type:
                            name: varchar
                            max_length: 1
                        table_constraints:
                          - type: unique
                    """;
    assertEquals(expectedYaml, yaml);
  }
}
