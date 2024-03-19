package com.fakedatagenerator.commands;

import com.fakedatagenerator.seeders.Seeder;
import com.fakedatagenerator.seeders.SeederFactory;
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
@Command(command = "generate", group = "Generate")
public class GenerateCommand {
  private static final Logger logger = LogManager.getLogger(GenerateCommand.class);
  @Autowired private final EntityConfig entityConfig;

  public GenerateCommand(EntityConfig entityConfig) {
    this.entityConfig = entityConfig;
  }

  @Command(command = "data", description = "Generates data for a specified table")
  public void generateData(
      @Option(
              shortNames = {'n'},
              required = true)
          Integer numEntities,
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

  @Command(
      command = "script",
      description = "Creates a SQL script for loading the fake data into a database.")
  public void generateScript(
      @Option(shortNames = 'p', required = true) String filePath,
      @Option(shortNames = 't') String tableName) {
    FileFormats fileFormat;
    if (filePath.endsWith("sql")) {
      fileFormat = FileFormats.SQL; // Default until adding other writers
    } else {
      throw new UnsupportedOperationException("File format not supported");
    }
    Writer writer;
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

  @Command(
      command = "seed",
      description = "Loads a csv into a table for already predefined values.")
  public void seedData(
      @Option(shortNames = 'p', required = true) String seedPath,
      @Option(shortNames = 't', required = true) String tableName) {
    FileFormats fileFormat;
    if (seedPath.endsWith("csv")) {
      fileFormat = FileFormats.CSV; // Default until adding other writers
    } else {
      throw new UnsupportedOperationException("File format not supported");
    }
    Seeder seeder = SeederFactory.getSeeder(fileFormat);
    seeder.seed(this.entityConfig.getDataManager().getTables().get(tableName), seedPath);
  }
}
