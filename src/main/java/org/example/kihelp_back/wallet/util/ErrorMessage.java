package org.example.kihelp_back.wallet.util;

public class ErrorMessage {
    private ErrorMessage() {}

    public static final String DEFAULT_WALLET_EXIST = "Default гаманець вже існує.";
    public static final String WALLET_EXIST_BY_NAME = "Гаманець з іменем %s вже існує.";
    public static final String WALLET_NOT_FOUND = "Гаманець з id %s не знайдено.";
    public static final String WALLET_AMOUNT_NOT_VALID = "На гаманці '%s' недостатньо коштів: баланс '%s' менший за запитану суму зняття '%s'.";
}
