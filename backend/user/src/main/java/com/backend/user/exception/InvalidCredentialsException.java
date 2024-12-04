package com.backend.user.exception;

// declares and throws a specific exception when a user attempts to log in with an invalid password

public class InvalidCredentialsException extends RuntimeException {

    private final int status;

    // Message of the exception
    private static final String MESSAGE = "Le mot de passe saisi est invalide.";

    public InvalidCredentialsException(int status) {
        super(MESSAGE);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}