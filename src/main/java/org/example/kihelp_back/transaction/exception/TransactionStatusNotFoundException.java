package org.example.kihelp_back.transaction.exception;

public class TransactionStatusNotFoundException extends RuntimeException {
    public TransactionStatusNotFoundException(String message) {
        super(message);
    }
}
