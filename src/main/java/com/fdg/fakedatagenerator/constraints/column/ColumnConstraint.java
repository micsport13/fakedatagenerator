package com.fdg.fakedatagenerator.constraints.column;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fdg.fakedatagenerator.constraints.Constraint;

/** The interface Column constraint. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = ColumnCheckConstraint.class, name = "check_constraint"),
  @JsonSubTypes.Type(value = NotNullConstraint.class, name = "not_null")
})
public interface ColumnConstraint extends Constraint {
  boolean conflictsWith(ColumnConstraint other);
}
