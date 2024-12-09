package com.backend.user.exception;

public class InvalidTokenException extends RuntimeException {

    private final int status;

    // Message of the exception
    private static final String MESSAGE = "Le jeton JWT est invalide.";

    public InvalidTokenException(int status) {
        super(MESSAGE);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
