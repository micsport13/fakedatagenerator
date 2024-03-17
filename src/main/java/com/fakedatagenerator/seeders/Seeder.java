package com.fakedatagenerator.seeders;

import com.fakedatagenerator.table.Table;

public interface Seeder {
  void seed(Table table, String path);
}
