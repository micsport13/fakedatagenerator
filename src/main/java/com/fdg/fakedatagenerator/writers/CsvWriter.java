package com.fdg.fakedatagenerator.writers;

import com.fdg.fakedatagenerator.table.Table;
import java.util.ArrayList;
import java.util.List;

public class CsvWriter implements Writer {
  private final List<Table> tables = new ArrayList<>();

  // TODO: Finish this class
  public CsvWriter(Table... tables) {
    this.tables.addAll(List.of(tables));
  }

  @Override
  public String write() {
    return null;
  }
}
