package com.fdg.fakedatagenerator.exceptions;

/** The type Unique constraint exception. */
public class UniqueConstraintException extends ValidationException {
  /**
   * Instantiates a new Unique constraint exception.
   *
   * @param message the message
   */
  public UniqueConstraintException(String message) {
    super(message);
  }
}
