package com.fdg.fakedatagenerator.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdg.fakedatagenerator.table.Table;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class EntityConfig {
  @Autowired Faker faker;
  @Autowired private ObjectMapper objectMapper;

  public List<Table> loadConfig(String filePath) throws IOException {
    InjectableValues.Std inject = new InjectableValues.Std();
    inject.addValue(Faker.class, this.faker);
    this.objectMapper.setInjectableValues(inject);
    return this.objectMapper.readValue(new File(filePath), new TypeReference<>() {});
  }

  public void writeConfig(String filePath, DataManager dataManager) throws IOException {
    this.objectMapper.writeValue(new File(filePath), dataManager.getTables().values());
  }
}
