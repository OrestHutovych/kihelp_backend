package org.example.kihelp_back.wallet.util;

public class WalletErrorMessage {
    private WalletErrorMessage() {}

    public static final String DEFAULT_WALLET_EXIST = "Default гаманець вже існує.";
    public static final String WALLET_EXIST_BY_NAME = "Гаманець з іменем %s вже існує.";
    public static final String WALLET_NOT_FOUND = "Гаманець не знайдено у користувача.";
    public static final String WALLET_AMOUNT_NOT_VALID = "На гаманці недостатньо коштів. Баланс менший за запитану суму зняття '%s'.";
    public static final String MIN_AMOUNT = "Мінімальна сума поповнення 10 UAH.";
    public static final String CARD_NUMBER_NOT_VALID = "Невірний номер картки.";
    public static final String WALLET_WITHDRAW_AMOUNT_NOT_VALID = "Мінімальна сума виводу 1000 UAH.";
    public static final String WALLET_JAR_INFO = "Не знайдено банки для поповнення.";
    public static final String WALLET_USER_ERR = "Користувач не може переглянути чужий гаманець";
}
