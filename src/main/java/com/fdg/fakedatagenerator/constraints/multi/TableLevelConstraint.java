package com.fdg.fakedatagenerator.constraints.multi;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fdg.fakedatagenerator.constraints.Constraint;
import com.fdg.fakedatagenerator.constraints.single.ForeignKeyConstraint;

/** The interface Table constraint. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = MultiColumnCheckConstraint.class, name = "table_check"),
  @JsonSubTypes.Type(value = UniqueLevelConstraint.class, name = "unique"),
  @JsonSubTypes.Type(value = ForeignKeyConstraint.class, name = "foreign_key"),
  @JsonSubTypes.Type(value = PrimaryKeyConstraint.class, name = "primary_key")
})
public interface TableLevelConstraint extends Constraint {}
