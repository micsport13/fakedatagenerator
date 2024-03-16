package com.fakedatagenerator.generators;

import com.fakedatagenerator.datatypes.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = FakerBothifyGenerator.class, name = "bothify"),
  @JsonSubTypes.Type(value = FakerCollectionGenerator.class, name = "collection"),
  @JsonSubTypes.Type(value = FakerExpressionGenerator.class, name = "expression"),
  @JsonSubTypes.Type(value = FakerMethodGenerator.class, name = "method"),
  @JsonSubTypes.Type(value = FakerLetterifyGenerator.class, name = "letterify"),
  @JsonSubTypes.Type(value = FakerRegexifyGenerator.class, name = "regexify"),
  @JsonSubTypes.Type(value = FakerTemplatifyGenerator.class, name = "templatify"),
  @JsonSubTypes.Type(
      value = ForeignValueGenerator.class,
      names = {"foreign_values", "foreign_value"}),
  @JsonSubTypes.Type(value = SequentialValueGenerator.class, name = "sequence")
})
public interface ValueGenerator {
  Object nextValue();
}
