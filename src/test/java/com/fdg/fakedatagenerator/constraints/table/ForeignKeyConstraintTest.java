package com.fdg.fakedatagenerator.constraints.table;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.datatypes.IntegerDataType;
import com.fdg.fakedatagenerator.schema.Schema;
import com.fdg.fakedatagenerator.table.Table;
import org.junit.jupiter.api.BeforeAll;

/** The type Foreign key constraint test. */
class ForeignKeyConstraintTest {
  private ForeignKeyConstraint foreignKeyConstraint;
  private Table foreignTable;
  private String foreignColumnName;

  @BeforeAll
  void setUp() {
    Schema schema = new Schema(new Column<>("id", new IntegerDataType()));
    this.foreignTable = new Table("foreignTable", schema);

    this.foreignColumnName = "foreignColumn";
    this.foreignKeyConstraint = new ForeignKeyConstraint(foreignTable, foreignColumnName);
  }
}
