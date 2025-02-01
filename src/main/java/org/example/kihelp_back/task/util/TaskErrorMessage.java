package org.example.kihelp_back.task.util;

public class TaskErrorMessage {
    private TaskErrorMessage() {}

    public static final String TASK_EXIST = "Завдання з назвою '%s' вже існує для викладача '%s' у цьому предметі.";
    public static final String TASK_NOT_FOUND = "Завдання з ID '%s' не знайдено.";
    public static final String TYPE_NOT_VALID = "Тип завдання '%s' не знайдено.";
    public static final String TITLE_BLANK_NOT_VALID = "Заголовок завдання не може бути порожнім.";
    public static final String IDENTIFIER_BLANK_NOT_VALID = "Ідентифікатор завдання не може бути порожнім.";
    public static final String IDENTIFIER_NOT_VALID = "Ідентифікатор має містити %s";
    public static final String PRICE_MIN_NOT_VALID = "Ціна повинна бути більшою за 1 UAH.";
    public static final String USER_BANNED_BY_RESELLER_ACTIVITY = "Користувача із Telegram ID %s було забанено за підозрілу активність. Bиявлено повторні спроби масового придбання завдань.";
    public static final String ARGS_NOT_NULL = "Завдання не може мати null аргументів";
    public static final String TASK_GENERATE_RESPONSE_NULL = "Отримано null-відповідь від сервісу генерації завдань";
}
