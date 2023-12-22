package com.fdg.fakedatagenerator.constraints.table;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fdg.fakedatagenerator.constraints.Constraint;

/** The interface Table constraint. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = TableCheckConstraint.class, name = "table_check"),
  @JsonSubTypes.Type(value = UniqueConstraint.class, name = "unique"),
  @JsonSubTypes.Type(value = ForeignKeyConstraint.class, name = "foreign_key"),
  @JsonSubTypes.Type(value = PrimaryKeyConstraint.class, name = "primary_key")
})
public interface TableConstraint extends Constraint {}
