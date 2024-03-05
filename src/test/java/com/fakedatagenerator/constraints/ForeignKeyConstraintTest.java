package com.fakedatagenerator.constraints;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.datatypes.IntegerDataType;
import com.fakedatagenerator.schema.Schema;
import com.fakedatagenerator.table.Table;
import org.junit.jupiter.api.BeforeEach;

/** The type Foreign key constraint test. */
class ForeignKeyConstraintTest {
  private ForeignKeyConstraint foreignKeyConstraint;
  private Table foreignTable;
  private String foreignColumnName;

  @BeforeEach
  void setUp() {
    Schema schema = new Schema(new Column<>("id", new IntegerDataType()));
    this.foreignTable = new Table("foreignTable", schema);

    this.foreignColumnName = "foreignColumn";
    this.foreignKeyConstraint = new ForeignKeyConstraint(foreignTable, foreignColumnName);
  }
}
