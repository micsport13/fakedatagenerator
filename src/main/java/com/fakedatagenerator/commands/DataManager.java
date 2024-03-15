package com.fakedatagenerator.commands;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.row.Row;
import com.fakedatagenerator.table.Table;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Getter
@Component
@Log4j2
public class DataManager {

  private final Map<String, Table> tables = new HashMap<>();

  public void generateData(Integer numEntities, String tableName) {
    if (numEntities == null || numEntities <= 0) {
      throw new IllegalArgumentException("Number of entities must be greater than 0");
    }
    Table table = this.tables.get(tableName);
    if (table == null) {
      throw new IllegalArgumentException(
          String.format("Table with name %s was not found.  Was the table loaded?", tableName));
    }
    for (int i = 0; i < numEntities; i++) {
      Row.Builder rowBuilder = new Row.Builder(table.getColumns().toArray(new Column[0]));
      for (Column column : table.getColumns()) {
        rowBuilder.withColumnValue(column.getName(), column.getValueGenerator().nextValue());
      }
      table.add(rowBuilder.build());
    }
  }

  public void addTable(Table table) {
    this.tables.put(table.getName(), table);
  }

  public Table getTable(String tableName) {
    return this.tables.get(tableName);
  }
}
