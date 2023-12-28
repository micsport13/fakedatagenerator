package com.fdg.fakedatagenerator.writers;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.row.Row;
import com.fdg.fakedatagenerator.table.Table;
import java.util.stream.Collectors;

public class SqlWriter implements Writer {
  private static final String INSERT_INTO = "INSERT INTO ";
  private static final String VALUES = " VALUES ";
  private final Table table;

  public SqlWriter(Table table) {
    this.table = table;
  }

  @Override
  public String write() {
    StringBuilder sb = new StringBuilder();
    sb.append(INSERT_INTO);
    sb.append(" ").append(this.table.getName()).append(" ");
    sb.append("(")
        .append(
            this.table.getSchema().getColumns().stream()
                .map(Column::getName)
                .collect(Collectors.joining(", ")))
        .append(") ");
    sb.append(VALUES);
    for (Row row : this.table.getEntities()) {
      sb.append("(");
      sb.append(
          row.getColumns().stream()
              .map(
                  column ->
                      column
                              .getDataType()
                              .getClass()
                              .getSimpleName()
                              .equalsIgnoreCase("VarcharDataType")
                          ? wrapInQuotes(row.getColumnValue(column))
                          : row.getColumnValue(column).toString())
              .collect(Collectors.joining(", ")));
      sb.append("),");
    }
    sb.setLength(sb.length() - 1);
    sb.append(";");
    return sb.toString();
  }

  private String wrapInQuotes(String value) {
    return "'" + value + "'"; // TODO: Scrub string before quoting
  }
}
