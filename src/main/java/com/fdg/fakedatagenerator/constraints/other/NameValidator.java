package com.fdg.fakedatagenerator.constraints.other;

import java.util.regex.Pattern;

public class NameValidator {
  public static final Pattern NAME_VALIDATION = Pattern.compile("[A-z]\\w+");
  // TODO: Verify if this is the valid naming for tables/columns
  private static final int MAX_NAME_LENGTH = 31;

  public static void validate(String value) {
    if (value.length() > MAX_NAME_LENGTH) {
      throw new IllegalArgumentException(
          "Name cannot be longer than " + MAX_NAME_LENGTH + " characters");
    }
    if (!NAME_VALIDATION.matcher(value).matches()) {
      throw new IllegalArgumentException("Name must only contain characters A-Z, 0-9, or _");
    }
  }
}
