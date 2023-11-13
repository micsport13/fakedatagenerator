package com.fdg.fakedatagenerator.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

/** The type Data generator. */
public class DataGenerator {
  private static final Logger logger = LogManager.getLogger(DataGenerator.class);
  @Autowired private final DataManager dataManager;

  public DataGenerator(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  @Command(command = "generate")
  public void generateData(Integer numEntities, @Option(required = false) String filePath) {
    this.dataManager.generateData(numEntities, filePath);
  }
}
