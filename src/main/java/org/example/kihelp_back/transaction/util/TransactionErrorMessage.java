package org.example.kihelp_back.transaction.util;

public class TransactionErrorMessage {
    private TransactionErrorMessage() {}

    public static final String TRANSACTION_EXISTS = "Транзакція з Transaction ID '%s' вже існує";
    public static final String TRANSACTION_NOT_FOUND = "Транзакція з Transaction ID '%s' не знайдено";
    public static final String TRANSACTION_TYPE_IS_NOT_WITHDRAW = "Тип транзакції не відповідає WITHDRAW";
    public static final String TRANSACTION_STATUS_NOT_IN_PROGRESS = "Статус транзакції не відповідає IN_PROGRESS";
    public static final String TRANSACTIONS_NOT_BY_USER = "Користувач не може переглядати чужі транзакції";
}
