package org.example.kihelp_back.transaction.util;

public class ErrorMessage {
    private ErrorMessage() {}

    public static final String TRANSACTION_EXISTS = "Транзакція з Transaction ID: '%s' вже існує.";
    public static final String TRANSACTION_ID_NOT_BLANK = "Transaction id не повинне бути пустим.";
    public static final String TRANSACTION_AMOUNT_MIN = "Мінімальна саму транзакції має бути 10 UAH";
    public static final String TRANSACTION_NOT_FOUND = "Транзакція з Transaction ID: '%s' не знайдено.";
    public static final String TRANSACTION_STATUS_NOT_FOUND = "Статус транзакції '%s' не знайдено.";
    public static final String TRANSACTION_TYPE_IS_NOT_WITHDRAW = "Тип транзакції з Transaction ID: '%s' не є WITHDRAW.";
}
