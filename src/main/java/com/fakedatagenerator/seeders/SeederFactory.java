package com.fakedatagenerator.seeders;

import com.fakedatagenerator.writers.FileFormats;

public class SeederFactory {
  public static Seeder getSeeder(FileFormats format) {
    if (format == FileFormats.CSV) {
      return new CSVSeeder();
    } else {
      throw new UnsupportedOperationException("File format not supported");
    }
  }
}
