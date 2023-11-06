package com.fdg.fakedatagenerator.row;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.datatypes.DataType;
import com.fdg.fakedatagenerator.exceptions.MismatchedDataTypeException;
import java.util.*;
import lombok.Getter;

/** The type Row. */
@Getter
public class Row {

  private final Map<Column<?>, Object> columnValueMapping;

  private Row(Builder builder) {
    this.columnValueMapping = builder.columnValueMapping;
  }

  /**
   * Gets columns.
   *
   * @return the columns
   */
  public Set<Column<?>> getColumns() {
    return new LinkedHashSet<>(this.columnValueMapping.keySet());
  }

  public <T> void setColumnValue(Column<?> column, T columnValue) {
    if (columnValue == null
        || column.getDataType().isCompatible(String.valueOf(columnValue))) {
      this.columnValueMapping.put(column, columnValue);
    } else {
      throw new MismatchedDataTypeException(
          "Value is not compatible with the column's data type.");
    }
  }
  /**
   * Sets column value.
   *
   * @param columnName the column name
   * @param columnValue the column value
   */
  public <T> void setColumnValue(String columnName, T columnValue) {
    Optional<Column<?>> column = getColumnByName(columnName);
    if (column.isPresent()) {
      if (columnValue == null
          || column.get().getDataType().isCompatible(String.valueOf(columnValue))) {
        this.columnValueMapping.put(column.get(), columnValue);
      } else {
        throw new MismatchedDataTypeException(
            "Value is not compatible with the column's data type.");
      }
    } else {
      throw new IllegalArgumentException("Column does not exist.");
    }
  }

  @SuppressWarnings("unchecked")
  public <T> T getColumnValue(Column<?> column) {
    Object value = columnValueMapping.get(column);
    if (value != null) {
      DataType<?> dataType = column.getDataType();
      return (T) dataType.cast(value.toString()); // TODO: Does this need to have types?
    }
    return null; // Handle the case where the value is null
  }

  @SuppressWarnings("unchecked")
  public <T> T getColumnValue(String columnName) {
    Optional<Column<?>> columnOptional = getColumnByName(columnName);
    if (columnOptional.isPresent()) {
      Column<?> column = columnOptional.get();
      Object value = columnValueMapping.get(column);
      DataType<?> dataType = column.getDataType();
      return (T) dataType.cast(value.toString()); // TODO: Does this need to have types?
    }
    return null; // Handle the case where the value is null
  }

  /**
   * To record string.
   *
   * @return the string
   */
  public String toRecord() {
    StringBuilder string = new StringBuilder();
    for (Column<?> column : this.columnValueMapping.keySet()) {
      Object value = this.columnValueMapping.get(column);
      string.append(value).append(",");
    }
    string.deleteCharAt(string.length() - 1);
    return string.toString();
  }

  /**
   * Gets column by name.
   *
   * @param columnName the column name
   * @return the column by name
   */
  public Optional<Column<?>> getColumnByName(String columnName) {
    for (Column<?> column : this.columnValueMapping.keySet()) {
      if (Objects.equals(columnName, column.getName())) {
        return Optional.of(column);
      }
    }
    return Optional.empty();
  }

  public Optional<Object> getValue(String columnName) {
    Optional<Column<?>> optionalColumn = getColumnByName(columnName);
    return optionalColumn.map(
        column -> {
          return this.getColumnValueMapping().get(column);
        });
  }

  @Override
  public String toString() {
    StringBuilder string = new StringBuilder();
    for (Map.Entry<Column<?>, ?> entry : columnValueMapping.entrySet()) {
      Column<?> column = entry.getKey();
      Object value = entry.getValue();
      string
          .append(column.toString())
          .append("\nValue: ")
          .append(value)
          .append("\n====================\n");
    }
    return string.toString();
  }

  /** The type SchemaBuilder. */
  public static class Builder {
    private final Map<Column<?>, Object> columnValueMapping = new LinkedHashMap<>();

    /**
     * Instantiates a new SchemaBuilder.
     *
     * @param columnList the column list
     */
    public Builder(Column<?>... columnList) {
      for (Column<?> column : columnList) {
        this.columnValueMapping.put(Objects.requireNonNull(column), null);
      }
    }

    /**
     * Add column builder.
     *
     * @param column the column
     * @return the builder
     */
    public Builder addColumn(Column<?> column) {
      this.columnValueMapping.put(column, null);
      return this;
    }

    /**
     * With column value builder.
     *
     * @param columnName the column name
     * @param value the value
     * @return the builder
     */
    public Builder withColumnValue(String columnName, Object value) throws ClassCastException {
      if (value == null) {
        throw new IllegalArgumentException("Value cannot be null if calling this method");
      }
      for (Column<?> column : this.columnValueMapping.keySet()) {
        if (column.getName().equals(columnName)
            && column.getDataType().isCompatible(String.valueOf(value))) {
          this.columnValueMapping.put(column, value);
          return this;
        }
        if (column.getName().equals(columnName)
            && !column.getDataType().isCompatible(String.valueOf(value))) {
          throw new IllegalArgumentException(
              "Value does not match the column type of "
                  + column.getDataType()
                  + " of the column: "
                  + columnName);
        }
      }
      return this;
    }

    /**
     * Build entity.
     *
     * @return the entity
     */
    public Row build() {
      return new Row(this);
    }
  }
}
