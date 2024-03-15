package com.fakedatagenerator.commands;

import com.fakedatagenerator.table.Table;
import com.fakedatagenerator.writers.FileFormats;
import com.fakedatagenerator.writers.Writer;
import com.fakedatagenerator.writers.WriterFactory;
import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

/** The type Data generators. */
@Command(command = "generate", group = "generate")
public class GenerateCommand {
  private static final Logger logger = LogManager.getLogger(GenerateCommand.class);
  @Autowired private final EntityConfig entityConfig;

  public GenerateCommand(EntityConfig entityConfig) {
    this.entityConfig = entityConfig;
  }

  @Command(command = "data")
  public void generateData(
      @Option(shortNames = {'n'}) Integer numEntities,
      @Option(shortNames = 't', required = true) String tableName,
      @Option(shortNames = 'p') String filePath)
      throws IOException {
    if (filePath == null) {
      this.entityConfig.getDataManager().generateData(numEntities, tableName);
    } else {
      this.entityConfig.loadConfig(filePath);
      this.entityConfig.getDataManager().generateData(numEntities, tableName);
    }
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
              fileFormat,
              this.entityConfig.getDataManager().getTables().values().toArray(new Table[0]));
    } else {
      writer =
          WriterFactory.getWriter(
              fileFormat, this.entityConfig.getDataManager().getTables().get(tableName));
    }
    File file = new File(filePath);
    try (var fileWriter = new java.io.FileWriter(file)) {
      fileWriter.write(writer.write());
    } catch (java.io.IOException e) {
      logger.error(e.getMessage());
    }
  }
}
