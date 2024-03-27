package com.fakedatagenerator.generators.factories;

import com.fakedatagenerator.generators.ForeignValueGenerator;
import com.fakedatagenerator.generators.ValueGenerator;
import com.fakedatagenerator.table.Table;
import com.fakedatagenerator.utils.Primitives;
import java.util.Map;
import net.datafaker.Faker;

public class FakerForeignValueGeneratorFactory implements ValueGeneratorFactory {
  private final Faker faker;

  public FakerForeignValueGeneratorFactory(Faker faker) {
    this.faker = faker;
  }

  @Override
  public ValueGenerator createValueGenerator(String valueGeneratorType, Map<String, Object> args) {
    Table table = (Table) args.get("table");
    String columnName = (String) args.get("column_name");
    return new ForeignValueGenerator(faker, table, columnName);
  }

  @Override
  public Map<String, Primitives> getOptions() {
    return null;
  }
}
