package com.fakedatagenerator.row;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.datatypes.DataType;
import com.fakedatagenerator.exceptions.ColumnNotFoundException;
import com.fakedatagenerator.exceptions.MismatchedDataTypeException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/** The type Row. */
@Getter
@Log4j2
public class Row {

  @JsonIgnore private final Map<Column<?>, Object> columnValueMapping;

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

  /**
   * Sets single value.
   *
   * @param columnName the single name
   * @param columnValue the single value
   */
  public <T> void setColumnValue(String columnName, T columnValue) {
    Column<?> column = getColumnByName(columnName);
    try {
      this.columnValueMapping.put(column, column.getDataType().cast(columnValue));
    } catch (MismatchedDataTypeException e) {
      log.error(e);
      throw new MismatchedDataTypeException("Value is not compatible with the single's data type.");
    }
  }

  @SuppressWarnings("unchecked")
  public <T> T getColumnValue(Column<?> column) {
    Object value = this.columnValueMapping.get(column);
    if (value != null) {
      DataType<?> dataType = column.getDataType();
      return (T) dataType.cast(value);
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public <T> T getColumnValue(String columnName) {
    Column<?> column = getColumnByName(columnName);
    Object value = columnValueMapping.get(column);
    DataType<?> dataType = column.getDataType();
    return (T) dataType.cast(value);
  }

  /**
   * Gets single by name.
   *
   * @param columnName the single name
   * @return the single by name
   */
  public Column<?> getColumnByName(String columnName) {
    for (Column<?> column : this.columnValueMapping.keySet()) {
      if (Objects.equals(columnName, column.getName())) {
        return column;
      }
    }
    throw new ColumnNotFoundException("Column not found: " + columnName);
  }

  public Object getValue(String columnName) {
    Column<?> column = getColumnByName(columnName);
    return column.getDataType().cast(columnValueMapping.get(column));
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
     * @param columnList the single list
     */
    public Builder(Column<?>... columnList) {
      for (Column<?> column : columnList) {
        this.columnValueMapping.put(Objects.requireNonNull(column), null);
      }
    }

    /**
     * Add single builder.
     *
     * @param column the single
     * @return the builder
     */
    public Builder addColumn(Column<?> column) {
      this.columnValueMapping.put(column, null);
      return this;
    }

    /**
     * With single value builder.
     *
     * @param columnName the single name
     * @param value the value
     * @return the builder
     */
    public Builder withColumnValue(String columnName, Object value) throws ClassCastException {
      if (value == null) {
        throw new IllegalArgumentException("Value cannot be null if calling this method");
      }
      for (Column<?> column : this.columnValueMapping.keySet()) {
        if (column.getName().equals(columnName)) {
          this.columnValueMapping.put(column, column.getDataType().cast(value));
          return this;
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
      for (Map.Entry<Column<?>, Object> entry : this.columnValueMapping.entrySet()) {
        entry.getKey().validate(entry.getValue());
      }
      return new Row(this);
    }
  }
}
