package com.fakedatagenerator.writers;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.row.Row;
import com.fakedatagenerator.table.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SqlWriter implements Writer { // TODO: Should I make classes for different databases?
  private static final String INSERT_INTO = "INSERT INTO ";
  private static final String VALUES = " VALUES ";
  private final List<Table> tables = new ArrayList<>();

  public SqlWriter(Table... tables) {
    this.tables.addAll(List.of(tables));
  }

  @Override
  public String write() {
    StringBuilder sb = new StringBuilder();
    for (Table table : this.tables) {
      sb.append(INSERT_INTO);
      sb.append(" ").append(table.getName()).append(" ");
      sb.append("(")
          .append(
              table.getColumns().stream().map(Column::getName).collect(Collectors.joining(", ")))
          .append(") ");
      sb.append(VALUES);
      for (Row row : table.getEntities()) {
        sb.append("(");
        sb.append( // TODO: Get rid of this ugly hack
            row.getColumns().stream()
                .map(
                    column -> {
                      try {
                        return column
                                    .getDataType()
                                    .getClass()
                                    .getMethod("cast", Object.class)
                                    .getReturnType()
                                    .getSuperclass()
                                != Number.class // TODO: Determine if class is a number
                            ? wrapInQuotes(row.getColumnValue(column).toString())
                            : row.getColumnValue(column).toString();
                      } catch (
                          NoSuchMethodException
                              e) { // TODO: Get rid of this exception and find a better way than
                        // reflection
                        throw new RuntimeException(e);
                      }
                    })
                .collect(Collectors.joining(", ")));
        sb.append("),\n");
      }
      sb.setLength(sb.length() - 2); // TODO: Figure out how to trim comma and newline more cleanly
      sb.append(";");
      sb.append("\n");
    }
    return sb.toString();
  }

  private String wrapInQuotes(String value) {
    return "'" + value.replace("'", "''") + "'"; // TODO: Scrub string before quoting
  }
}
