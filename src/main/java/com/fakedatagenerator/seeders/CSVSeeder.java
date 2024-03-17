package com.fakedatagenerator.seeders;

import com.fakedatagenerator.column.Column;
import com.fakedatagenerator.row.Row;
import com.fakedatagenerator.table.Table;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVSeeder implements Seeder {
  @Override
  public void seed(Table table, String path) {
    table.truncate();
    try {
      List<String[]> values = read(path);
      String[] headers = values.get(0);
      Row.Builder rowBuilder = new Row.Builder(table.getColumns().toArray(new Column[0]));
      for (String[] value : values) {
        if (value != headers) {
          for (int i = 0; i < value.length; i++) {
            rowBuilder.withColumnValue(headers[i], value[i]);
          }
          table.add(rowBuilder.build());
        }
      }
    } catch (IOException | CsvValidationException e) {
      e.printStackTrace();
    }
  }

  private List<String[]> read(String path) throws IOException, CsvValidationException {
    try (Reader reader = Files.newBufferedReader(Paths.get(path));
        CSVReader csvReader = new CSVReader(reader)) {
      List<String[]> records = new ArrayList<>();
      String[] record;
      while ((record = csvReader.readNext()) != null) {
        records.add(record);
      }
      return records;
    }
  }
}
