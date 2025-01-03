package org.example.kihelp_back.user.util;

public class ErrorMessage {
    private ErrorMessage() {}

    public static final String USER_IS_BANNED = "Користувач із зазначеним Telegram ID (%s) був заблокований через порушення встановлених правил поведінки.";
    public static final String USER_NOT_FOUND = "Користувач із зазначеним ID (%s) не знайдено.";
    public static final String USER_NOT_FOUND_BY_TG_ID = "Користувач із зазначеним Telegram ID (%s) не знайдено.";
    public static final String ROLE_NOT_FOUND = "Роль %s не знайдено.";
    public static final String USER_BAN_VALUE_NOT_NULL = "Значення value не має дорівнювати null.";
    public static final String USER_BAD_CREDENTIALS = "Bad credentials";
    public static final String JWT_TOKEN_EXPIRED = "JWT Expired";
    public static final String USER_ROLE_CHANGE_NOT_ALLOWED = "Не можна забрати роль 'ROLE_USER' у користувача";
}
