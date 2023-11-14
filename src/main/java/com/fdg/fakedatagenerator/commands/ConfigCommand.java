package com.fdg.fakedatagenerator.commands;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.serializers.ColumnConfig;
import java.io.IOException;
import java.nio.file.Path;

import com.fdg.fakedatagenerator.serializers.TableConfig;
import com.fdg.fakedatagenerator.table.Table;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;

@Log4j2
@Command(command = "config", group = "Config")
public class ConfigCommand {
  @Autowired private final DataManager dataManager;

  public ConfigCommand(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  @Command(command = "load", description = "Load configuration from file")
  public static void loadConfig(String path) { // TODO: Load entire configuration, not just column
    Path filePath = Path.of(System.getProperty("user.dir"), path);
    try {
      Column<?> columnDeserialized = ColumnConfig.loadConfig(filePath.toString());
      System.out.println(columnDeserialized);
    } catch (IOException e) {
      log.error(e);
    }
  }

  @Command(command = "save", description = "Write configuration to file")
  public void writeConfig(String path) {
    Path filePath = Path.of(System.getProperty("user.dir"), path);
    try {
      for (Table table : this.dataManager.getTables()) {
        TableConfig.writeConfig(filePath.toString(), table);
      }
    } catch (IOException e) {
      log.error(e);
    }
  }

  @Command(command = "print", description = "Prints out the current sessions configurations")
  public void printConfig() {
    System.out.println("Current session configurations:");
    this.dataManager.getTables().forEach(System.out::println);
    this.dataManager.getSchemas().forEach(System.out::println);
    this.dataManager.getColumns().forEach(System.out::println);
  }
}
