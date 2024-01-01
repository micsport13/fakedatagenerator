package com.fdg.fakedatagenerator.commands;

import com.fdg.fakedatagenerator.table.Table;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@Log4j2
@Command(command = "config", group = "Config")
public class ConfigCommand {
  @Autowired private final DataManager dataManager;
  @Autowired private final EntityConfig entityConfig;

  public ConfigCommand(DataManager dataManager, EntityConfig entityConfig) {
    this.dataManager = dataManager;
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
      List<Table> tables =
          entityConfig.loadConfig(
              filePath.toString()); // TODO: Load all objects into the data manager
      for (Table table : tables) {
        dataManager.addTable(table);
      }
    } catch (IOException e) {
      log.error(e);
    }
  }

  @Command(command = "save", description = "Write configuration to file")
  public void writeConfig(
      @Option(longNames = "path", shortNames = 'p', required = true) String path) {
    Path filePath = Path.of(System.getProperty("user.dir"), path);
    try {
      entityConfig.writeConfig(path, dataManager);
    } catch (IOException e) {
      log.error(e);
    }
  }

  @Command(command = "print", description = "Prints out the current sessions configurations")
  public void printConfig() {
    System.out.println("Current session configurations:");
    this.dataManager.getTables().forEach((key, value) -> System.out.println(value));
  }
}
