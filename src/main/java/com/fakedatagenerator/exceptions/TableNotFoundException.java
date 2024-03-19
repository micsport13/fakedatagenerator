package com.fakedatagenerator.exceptions;

public class TableNotFoundException extends ValidationException {
  public TableNotFoundException(String message) {
    super(message);
  }
}
