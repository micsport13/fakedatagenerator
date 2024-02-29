package com.fdg.fakedatagenerator.constraints;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/** The interface Constraint. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = MultiColumnCheckConstraint.class, name = "table_check"),
  @JsonSubTypes.Type(value = UniqueConstraint.class, name = "unique"),
  @JsonSubTypes.Type(value = ForeignKeyConstraint.class, name = "foreign_key"),
  @JsonSubTypes.Type(value = PrimaryKeyConstraint.class, name = "primary_key"),
  @JsonSubTypes.Type(value = AcceptedValuesCheckConstraint.class, name = "accepted_values"),
  @JsonSubTypes.Type(value = NotNullConstraint.class, name = "not_null"),
  @JsonSubTypes.Type(value = NumericCheckConstraint.class, name = "numeric_check")
})
public interface Constraint {
  /**
   * @param value the value
   */
  void validate(Object value) throws RuntimeException;
}
