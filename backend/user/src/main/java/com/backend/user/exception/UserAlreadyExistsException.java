package com.backend.user.exception;

// declares and throws a specific exception when a user tries to sign in with an already existing account

public class UserAlreadyExistsException extends RuntimeException {

    private final int status;

    // Message of the exception
    private static final String MESSAGE = "Un utilisateur existe déjà avec cette adresse e-mail. Veuillez vous connecter ou réinitialiser votre mot de passe en cas d'oubli.";

    public UserAlreadyExistsException(int status) {
        super(MESSAGE);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
