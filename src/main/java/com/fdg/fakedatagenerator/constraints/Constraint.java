package com.fdg.fakedatagenerator.constraints;

/** The interface Constraint. */
public interface Constraint {
  /**
   * @param value the value
   */
  void validate(Object value) throws RuntimeException;
}
