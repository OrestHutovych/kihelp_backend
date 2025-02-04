package org.example.kihelp_back.history.util;

public class HistoryErrorMessage {
    private HistoryErrorMessage() {}

    public static final String USER_ROLE_INVALID = "Доступ заборонено. Користувач із Telegram ID: '%s' не має ролі розробника.";
    public static final String HISTORY_NOT_FOUND = "History з ID %s для розробника не знайдено.";
    public static final String HISTORY_NOT_USER = "Користувач не може переглянути чужу історію.";
}
