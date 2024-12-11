package org.example.kihelp_back.user.exception;

public class UserIsBannedException extends RuntimeException {
    public UserIsBannedException(String message) {
        super(message);
    }
}
