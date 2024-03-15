package com.fakedatagenerator.commands;

import com.fakedatagenerator.table.Table;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class EntityConfig {
  @Autowired Faker faker;
  @Autowired private ObjectMapper objectMapper;
  @Getter @Autowired private DataManager dataManager;

  public void loadConfig(String filePath) throws IOException {
    dataManager.addTable(this.objectMapper.readValue(new File(filePath), Table.class));
  }

  public void writeConfig(String filePath) throws IOException {
    this.objectMapper.writeValue(new File(filePath), dataManager.getTables().values());
  }
}
