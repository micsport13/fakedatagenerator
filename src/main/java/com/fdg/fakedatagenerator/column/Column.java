package com.fdg.fakedatagenerator.column;

import com.fasterxml.jackson.annotation.*;
import com.fdg.fakedatagenerator.constraints.NameValidator;
import com.fdg.fakedatagenerator.datatypes.DataType;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/** Column class Contains a name, a data type, and a set of constraints */
@Getter
@JsonPropertyOrder({"name", "type", "generator"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Column<T extends DataType<?>> {
  @JsonProperty("name")
  private final @NotNull String name;

  @JsonProperty("type")
  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "name")
  private final @NotNull T dataType;

  @JsonProperty("generator")
  @Setter
  private ValueGenerator valueGenerator;

  public Column(String columnName, T dataType) {
    NameValidator.validate(columnName);
    this.name = Objects.requireNonNull(columnName);
    this.dataType = Objects.requireNonNull(dataType);
  }

  @JsonCreator
  public Column(
      @JsonProperty("name") String columnName,
      @JsonProperty("type") T dataType,
      @JsonProperty("generator") ValueGenerator valueGenerator) {
    this(columnName, dataType);
    this.valueGenerator = valueGenerator;
  }

  public void validate(Object value) {
    this.getDataType().cast(value);
  }

  @Override
  public int hashCode() {
    return this.name.hashCode() * this.dataType.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Column<?> column = (Column<?>) o;
    return this.name.equals(column.name) && this.dataType.equals(column.dataType);
  }

  @Override
  public String toString() {
    return "Column: " + this.name + "\nData Type: " + this.dataType;
  }
}
