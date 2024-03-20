package com.fakedatagenerator.commands;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.constraints.Constraint;
import com.fakedatagenerator.datatypes.DataType;
import com.fakedatagenerator.generators.ValueGenerator;
import com.fakedatagenerator.schema.Schema;
import com.fakedatagenerator.table.Table;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@Log4j2
@Command(command = "config", group = "Config")
public class ConfigCommand {
  private final EntityConfig entityConfig;

  @Autowired
  public ConfigCommand(EntityConfig entityConfig) {
    this.entityConfig = entityConfig;
  }

  @Command(command = "load", description = "Load configuration from file")
  public void loadConfig(
      @Option(longNames = "path", shortNames = 'p', required = true) String path) {
    Path filePath =
        Path.of(
            System.getProperty("user.dir"),
            path); // TODO: Figure out how to set path better than this
    try {
      entityConfig.loadConfig(filePath.toString());
    } catch (IOException e) {
      log.error(e);
    }
  }

  @Command(command = "save", description = "Write configuration to file")
  public void writeConfig(
      @Option(longNames = "path", shortNames = 'p', required = true) String path) {
    Path filePath = Path.of(System.getProperty("user.dir"), path);
    try {
      entityConfig.writeConfig(path);
    } catch (IOException e) {
      log.error(e);
    }
  }

  @Command(command = "print", description = "Prints out the current sessions configurations")
  public void printConfig() {
    System.out.println("Current session configurations:");
    this.entityConfig
        .getDataManager()
        .getTables()
        .forEach((key, value) -> System.out.println(value));
  }

  @Command(command = "build", description = "Build a configuration file", hidden = true)
  public void buildConfig() {
    String options = "";
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("Options: \n1. (C) Create Table\n2. (Q) Quit");
      options = scanner.nextLine();
      if (options.equalsIgnoreCase("q")) {
        break;
      }
      if (options.equalsIgnoreCase("c")) {
        Table table = buildTable();
        this.entityConfig.getDataManager().addTable(table);
      }
    }
  }

  private Table buildTable() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Please enter a table name:");
    String tableName = scanner.nextLine();
    Schema schema = new Schema();
    Map<String, Column> columns = new HashMap<>();
    List<Constraint> constraints = new ArrayList<>();
    while (true) {
      System.out.println(
          """
      Options (You must create columns before you can create constraints):
      1. Create Column
      2. Create Constraint
      3. Quit""");
      String option = scanner.nextLine();
      if (option.equals("3")) {
        break;
      }
      if (option.equals("1")) {
        System.out.println("Please enter a column name");
        String columnName = scanner.nextLine();
        System.out.println("Enter a data type");
        DataType<?> dataType = null;
        ValueGenerator valueGenerator = null;
        Column column = new Column(columnName, dataType, valueGenerator);
        columns.put(column.getName(), column);
      }
      if (option.equals("2")) {
        System.out.println("Please enter a constraint type");
        String constraintType = scanner.nextLine();

        List<Column> constraintColumns = new ArrayList<>();
        System.out.println("Please enter the columns that apply to this constraint");
        while (!scanner.nextLine().equals("q")) {
          constraintColumns.add(columns.get(scanner.nextLine()));
        }
        Constraint constraint = null;
        schema.addConstraint(constraint, constraintColumns.toArray(new Column[0]));
      }
    }
    return new Table(tableName, schema);
  }
}
