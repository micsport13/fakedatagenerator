package com.fdg.fakedatagenerator.constraints.single;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fdg.fakedatagenerator.constraints.Constraint;
import com.fdg.fakedatagenerator.constraints.multi.PrimaryKeyConstraint;
import com.fdg.fakedatagenerator.constraints.multi.UniqueLevelConstraint;

/** The interface Column constraint. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = AcceptedValuesCheckConstraint.class, name = "accepted_values"),
  @JsonSubTypes.Type(value = NotNullLevelConstraint.class, name = "not_null"),
  @JsonSubTypes.Type(value = NumericCheckConstraint.class, name = "numeric_check"),
  @JsonSubTypes.Type(value = UniqueLevelConstraint.class, name = "unique"),
  @JsonSubTypes.Type(value = PrimaryKeyConstraint.class, name = "primary_key")
})
public interface ColumnLevelConstraint extends Constraint {}
