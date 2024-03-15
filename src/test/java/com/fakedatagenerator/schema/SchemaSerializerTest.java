package com.fakedatagenerator.schema;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.constraints.PrimaryKeyConstraint;
import com.fakedatagenerator.constraints.UniqueConstraint;
import com.fakedatagenerator.datatypes.IntegerDataType;
import com.fakedatagenerator.datatypes.VarcharDataType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
class SchemaSerializerTest {

  @Autowired private ObjectMapper objectMapper;

  @Test
  public void serialize_Schema_OutputsCorrectSerialization() throws IOException {
    Column intColumn = new Column("intColumn", new IntegerDataType());
    Column varcharColumn = new Column("varcharColumn", new VarcharDataType());
    Schema schema = new Schema(intColumn, varcharColumn);
    schema.addConstraint(new PrimaryKeyConstraint(), intColumn);
    schema.addConstraint(new UniqueConstraint(), varcharColumn);
    String yaml = objectMapper.writeValueAsString(schema);
    // Assert
    String expectedYaml =
        """
                    columns:
                      - name: intColumn
                        data_type:
                          type: integer
                      - name: varcharColumn
                        data_type:
                          type: varchar
                          max_length: 1
                    constraints:
                      - constraint:
                          type: primary_key
                        columns:
                          - intColumn
                      - constraint:
                          type: unique
                        columns:
                          - varcharColumn
                    """;
    assertEquals(expectedYaml, yaml);
  }
}
