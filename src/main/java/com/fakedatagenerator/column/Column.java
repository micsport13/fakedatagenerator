package com.fakedatagenerator.column;

import com.fakedatagenerator.constraints.NameValidator;
import com.fakedatagenerator.datatypes.DataType;
import com.fakedatagenerator.generators.ValueGenerator;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.Getter;

/** Column class Contains a name, a data type, and a set of constraints */
@Getter
@JsonPropertyOrder({"name", "data_type", "generators"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Column {

  private final @NotNull String name;

  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
  @JsonProperty("data_type")
  @Getter
  private final @NotNull DataType<?> dataType;

  @JsonProperty("generator")
  private ValueGenerator valueGenerator;

  public Column(String columnName, DataType<?> dataType) {
    NameValidator.validate(columnName);
    this.name = Objects.requireNonNull(columnName);
    this.dataType = Objects.requireNonNull(dataType);
  }

  @JsonCreator
  public Column(
      @JsonProperty("name") String columnName,
      @JsonProperty("data_type") DataType<?> dataType,
      @JsonProperty("generator") ValueGenerator valueGenerator) {
    this(columnName, dataType);
    this.valueGenerator = valueGenerator;
  }

  public void validate(Object value) {
    this.dataType.cast(value);
  }

  @Override
  public int hashCode() {
    return this.name.hashCode() * this.dataType.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Column column = (Column) o;
    return this.name.equals(column.name) && this.dataType.equals(column.dataType);
  }

  @Override
  public String toString() {
    if (this.valueGenerator == null) {
      return "Column: " + this.name + "\nData Type: " + this.dataType + "\nGenerator: null";
    }
    return "Column: "
        + this.name
        + "\nData Type: "
        + this.dataType
        + "\nGenerator: "
        + this.valueGenerator;
  }
}
