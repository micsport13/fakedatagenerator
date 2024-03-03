package com.fdg.fakedatagenerator.commands;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.row.Row;
import com.fdg.fakedatagenerator.table.Table;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class DataManager {

  @Autowired @Getter private final EntityConfig entityConfig;

  @Getter private final Map<String, Table> tables = new HashMap<>();

  public DataManager(EntityConfig entityConfig) {
    this.entityConfig = entityConfig;
  }

  public void generateData(Integer numEntities, String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("File path cannot be null");
    }
    if (numEntities == null || numEntities <= 0) {
      throw new IllegalArgumentException("Number of entities must be greater than 0");
    }
    Table table = entityConfig.loadConfig(filePath);
    this.tables.put(table.getName(), table);
    for (int i = 0; i < numEntities; i++) {
      Row.Builder rowBuilder = new Row.Builder(table.getColumns().toArray(new Column[0]));
      for (Column<?> column : table.getColumns()) {
        rowBuilder.withColumnValue(column.getName(), column.getValueGenerator().nextValue());
      }
      table.add(rowBuilder.build());
    }
  }

  public void addTable(Table table) {
    this.tables.put(table.getName(), table);
  }
}
