package com.backend.user.exception;

// declares and throws a specific exception when a user is not found with the provided email address

public class UserNotFoundException extends RuntimeException {

    private final int status;

    // Message of the exception
    private static final String MESSAGE = "Utilisateur introuvable avec l'adresse e-mail saisie.";

    public UserNotFoundException(int status) {
        super(MESSAGE);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}