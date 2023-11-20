package com.fdg.fakedatagenerator;

import com.fdg.fakedatagenerator.column.Column;
import com.fdg.fakedatagenerator.row.Row;
import com.fdg.fakedatagenerator.serializers.EntityConfig;
import com.fdg.fakedatagenerator.table.Table;
import net.datafaker.Faker;

import java.io.IOException;
import java.util.List;

public class TestMain {
  public static void main(String[] args) throws IOException {
    List<Table> table = EntityConfig.loadConfig("testScript.yml");
    /*
    Faker faker = new Faker();
    Row row = new
        Row.Builder(table.forEach(table -> table.getSchema().getColumns().toArray(new Column[0])))
            .withColumnValue("id", 1)
            .withColumnValue("first_name", faker.name().firstName())
            .withColumnValue("last_name", faker.name().lastName())
            .withColumnValue("age", 21)
            .build();
    System.out.println(row.toRecord());

     */


  }
}
