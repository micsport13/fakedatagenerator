package com.fdg.fakedatagenerator.commands;

import com.fdg.fakedatagenerator.table.Table;
import com.fdg.fakedatagenerator.writers.FileFormats;
import com.fdg.fakedatagenerator.writers.Writer;
import com.fdg.fakedatagenerator.writers.WriterFactory;
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
      @Option(shortNames = 't') String tableName,
      @Option(shortNames = 'f') String format) {
    Writer writer = null;
    FileFormats fileFormat = null;
    if (format == null) {
      fileFormat = FileFormats.SQL;
    } else {
      fileFormat = FileFormats.valueOf(format);
    }
    if (tableName == null) {
      writer =
          WriterFactory.getWriter(
              fileFormat, this.dataManager.getTables().values().toArray(new Table[0]));
    } else {
      writer = WriterFactory.getWriter(fileFormat, this.dataManager.getTables().get(tableName));
    }
    File file = new File(filePath);
    try (var fileWriter = new java.io.FileWriter(file)) {
      fileWriter.write(writer.write());
    } catch (java.io.IOException e) {
      logger.error(e.getMessage());
    }
  }
}
