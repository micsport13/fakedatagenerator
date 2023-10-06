package com.fdg.fakedatagenerator.exceptions;

/**
 * The type Unique constraint exception.
 */
public class UniqueConstraintException extends RuntimeException {
    /**
     * Instantiates a new Unique constraint exception.
     *
     * @param message the message
     */
    public UniqueConstraintException(String message) {
        super(message);
    }
}