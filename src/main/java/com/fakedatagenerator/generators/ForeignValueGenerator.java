package com.fakedatagenerator.generators;

import com.fakedatagenerator.table.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import lombok.Getter;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;

@JsonSerialize(using = ForeignValueGeneratorSerializer.class)
@JsonDeserialize(using = ForeignValueGeneratorDeserializer.class)
public class ForeignValueGenerator implements ValueGenerator {

  @JsonIgnore private final Faker faker;
  @Getter private final Table table;
  @Getter private final String columnName;

  @Autowired
  public ForeignValueGenerator(Faker faker, Table table, String columnName) {
    this.faker = faker;
    this.table = table;
    this.columnName = columnName;
  }

  @Override
  public Object nextValue() {
    List<Object> foreignValues = table.getColumnValues(columnName);
    if (foreignValues.isEmpty()) {
      throw new IllegalStateException(
          "Foreign column has no values.  Have you generated data for it?");
    }
    return foreignValues.get(faker.random().nextInt(foreignValues.size()));
  }
}
