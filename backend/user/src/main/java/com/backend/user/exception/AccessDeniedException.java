package com.backend.user.exception;

public class AccessDeniedException extends RuntimeException {

    private final int status;

    // Default message for the exception
    private static final String MESSAGE = "Seuls les administrateurs peuvent accéder à cette application.";


    public AccessDeniedException(int status) {
        super(MESSAGE);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}

