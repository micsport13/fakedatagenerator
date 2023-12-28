package com.fdg.fakedatagenerator.commands;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.row.Row;
import com.fdg.fakedatagenerator.schema.Schema;
import com.fdg.fakedatagenerator.table.Table;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@Log4j2
public class DataManager {

  private final Faker faker = new Faker();

  @Getter private final Map<String, Table> tables = new HashMap<>();
  @Getter private final Map<String, Column<?>> columns = new HashMap<>();
  @Getter private final List<Schema> schemas = new ArrayList<>();

  public void generateData(Integer numEntities, String tableName, String filePath)
      throws InvocationTargetException,
          IllegalAccessException,
          NoSuchMethodException,
          InstantiationException { // TODO: Make this generic for any table
    if (filePath != null) {
      try {
        EntityConfig entityConfig = new EntityConfig();
        Map<String, Table> tableMap =
            entityConfig.loadConfig(filePath).stream()
                .collect(Collectors.toMap(Table::getName, table -> table));
        this.tables.putAll(tableMap);
      } catch (IOException e) {
        log.error(e);
      }
    }
    if (this.tables.get(tableName) == null) {
      log.error("Table {} does not exist", tableName);
    } else {
      Table currentTable = this.tables.get(tableName);
      for (int i = 0; i < numEntities; i++) {
        Row.Builder rowBuilder =
            new Row.Builder(currentTable.getSchema().getColumns().toArray(new Column[0]));
        for (Column<?> column : currentTable.getSchema().getColumns()) {
          rowBuilder.withColumnValue(column.getName(), column.getValueGenerator().get());
        }
        currentTable.add(rowBuilder.build());
      }
    }
  }

  private void writeToOutput(String message, String filePath) {
    if (filePath != null && !filePath.isEmpty()) {
      try {
        log.info("Writing to file: " + filePath);
        PrintWriter writer = new PrintWriter(new FileWriter(filePath, true));
        writer.println(message);
        writer.close();
      } catch (IOException e) {
        log.error(e);
      }
    } else {
      System.out.println(message);
    }
  }

  public void addSchema(Column<?>... columns) {
    Schema schema = new Schema(columns);
    this.schemas.add(schema);
  }

  public void addTable(String tableName, Schema schema) {
    Table table = new Table(tableName, schema);
    this.tables.put(tableName, table);
  }

  public void addTable(Table table) {
    this.tables.put(table.getName(), table);
  }

  public void addColumn(Column<?> column) {
    this.columns.put(column.getName(), column);
  }
}
