package com.backend.user.exception;

// declares and throws a specific exception when a user attempts to access an unactivated account

public class AccountNotVerifiedException extends RuntimeException {

    private final int status;

    // Default message for the exception
    private static final String MESSAGE = "Votre compte n'est pas encore activé. Veuillez l'activer à partir du lien envoyé par e-mail pour vous connecter.";


    public AccountNotVerifiedException(int status) {
        super(MESSAGE);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}