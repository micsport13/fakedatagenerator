package com.fdg.fakedatagenerator.commands;

import com.fdg.fakedatagenerator.table.Table;
import com.fdg.fakedatagenerator.writers.SqlWriter;
import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

/** The type Data generator. */
@Command(command = "generate", group = "generate")
public class GenerateCommand {
  private static final Logger logger = LogManager.getLogger(GenerateCommand.class);
  @Autowired private final DataManager dataManager;

  public GenerateCommand(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  @Command(command = "data")
  public void generateData(
      @Option(shortNames = {'n'}) Integer numEntities,
      @Option(shortNames = 'p', required = true) String filePath)
      throws IOException {
    this.dataManager.generateData(numEntities, filePath);
  }

  @Command(command = "script")
  public void generateScript(
      @Option(shortNames = 'p', required = true) String filePath,
      @Option(shortNames = 't') String tableName) {
    SqlWriter sqlWriter = null;
    if (tableName == null) {
      sqlWriter = new SqlWriter(this.dataManager.getTables().values().toArray(new Table[0]));
    } else {
      sqlWriter = new SqlWriter(this.dataManager.getTables().get(tableName));
    }
    File file = new File(filePath);
    try (var fileWriter = new java.io.FileWriter(file)) {
      fileWriter.write(sqlWriter.write());
    } catch (java.io.IOException e) {
      logger.error(e.getMessage());
    }
  }
}
