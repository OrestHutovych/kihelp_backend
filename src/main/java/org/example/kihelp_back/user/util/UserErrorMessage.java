package org.example.kihelp_back.user.util;

public class UserErrorMessage {
    private UserErrorMessage() {}

    public static final String USER_IS_BANNED = "Користувач із Telegram ID %s заблокований за порушення правил";
    public static final String USER_NOT_FOUND = "Користувача із Telegram ID %s не знайдено";
    public static final String ROLE_NOT_FOUND = "Роль із іменем %s не знайдено";
    public static final String USER_BAD_CREDENTIALS = "Bad credentials";
    public static final String JWT_TOKEN_EXPIRED = "JWT Expired";
    public static final String USER_ROLE_CHANGE_NOT_ALLOWED = "Роль 'ROLE_USER' не можна видалити у користувача";
    public static final String INVALID_USER_DATA = "Виникла помилка при обробці користувача: %s";
    public static final String MISSING_USER_DATA = "Init data не знайдено";
    public static final String USER_AUTHENTICATION_FAILED = "Помилка процесу автентифікації";
}
