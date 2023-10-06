package com.fdg.fakedatagenerator.exceptions;

/**
 * The type Primary key constraint exception.
 */
public class PrimaryKeyConstraintException extends RuntimeException {
    /**
     * Instantiates a new Primary key constraint exception.
     *
     * @param message the message
     */
    public PrimaryKeyConstraintException(String message) {
        super(message);
    }
}
