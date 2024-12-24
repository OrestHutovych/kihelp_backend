package org.example.kihelp_back.transaction.exception;

public class TransactionTypeNotAllowedException extends RuntimeException {
    public TransactionTypeNotAllowedException(String message) {
        super(message);
    }
}
