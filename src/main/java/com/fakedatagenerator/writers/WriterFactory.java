package com.fakedatagenerator.writers;

import com.fakedatagenerator.table.Table;

public class WriterFactory {
  public static Writer getWriter(FileFormats fileFormat, Table... tables) {
    return switch (fileFormat) {
      case SQL -> new SqlWriter(tables);
      case CSV -> new CsvWriter(tables);
      default -> throw new IllegalArgumentException("Invalid file format");
    };
  }
}
