package com.fdg.fakedatagenerator.commands;

import com.fdg.fakedatagenerator.writers.SqlWriter;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
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

  @Command(command = "generate")
  public void generateData(
      @Option(shortNames = {'n'}) Integer numEntities,
      @Option(shortNames = {'t'}) String tableName,
      @Option(required = false) String filePath)
      throws InvocationTargetException,
          IllegalAccessException,
          NoSuchMethodException,
          InstantiationException {
    this.dataManager.generateData(numEntities, tableName, filePath);
  }

  @Command(command = "script")
  public void generateScript(
      @Option(required = true) String filePath, @Option(required = true) String tableName) {
    SqlWriter sqlWriter = new SqlWriter(this.dataManager.getTables().get(tableName));
    File file = new File(filePath);
    try (var fileWriter = new java.io.FileWriter(file)) {
      fileWriter.write(sqlWriter.write());
    } catch (java.io.IOException e) {
      logger.error(e.getMessage());
    }
  }
}
