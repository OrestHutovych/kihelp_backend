package org.example.kihelp_back.transaction.exception;

public class TransactionExistException extends RuntimeException {
    public TransactionExistException(String message) {
        super(message);
    }
}
