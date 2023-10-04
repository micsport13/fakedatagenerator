package com.fakedatagenerator.exceptions;

/**
 * The type Foreign key constraint exception.
 */
public class ForeignKeyConstraintException extends RuntimeException {
    /**
     * Instantiates a new Foreign key constraint exception.
     *
     * @param message the message
     */
    public ForeignKeyConstraintException(String message) {
        super(message);
    }
}
