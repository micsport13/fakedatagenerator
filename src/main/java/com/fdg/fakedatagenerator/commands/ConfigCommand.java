package com.fdg.fakedatagenerator.commands;

import com.fdg.fakedatagenerator.serializers.EntityConfig;
import com.fdg.fakedatagenerator.table.Table;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Log4j2
@Command(command = "config", group = "Config")
public class ConfigCommand {
  @Autowired private final DataManager dataManager;

  public ConfigCommand(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  @Command(command = "load", description = "Load configuration from file")
  public void loadConfig(
      @Option(longNames = "path", shortNames = 'p', required = true) String path) {
    Path filePath = Path.of(System.getProperty("user.dir"), path);
    try {
      List<Table> tables =
          EntityConfig.loadConfig(
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
      EntityConfig.writeConfig(path, dataManager);
    } catch (IOException e) {
      log.error(e);
    }
  }

  @Command(command = "print", description = "Prints out the current sessions configurations")
  public void printConfig() {
    System.out.println("Current session configurations:");
    this.dataManager.getTables().forEach(System.out::println);
  }
}
