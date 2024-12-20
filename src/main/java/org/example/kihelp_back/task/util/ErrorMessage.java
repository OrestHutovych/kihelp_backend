package org.example.kihelp_back.task.util;

public class ErrorMessage {
    public static final String TASK_EXIST = "Завдання з назвою %s вже існує для викладача %s у цьому предметі.";
    public static final String TASK_NOT_FOUND = "Завдання з id %s не знайдено.";
    public static final String TYPE_NOT_VALID = "Тип завдання %s не знайдено.";
    public static final String TITLE_BLANK_NOT_VALID = "Заголовок завдання не повинен будти пустим.";
    public static final String IDENTIFIER_BLANK_NOT_VALID = "Ідентифікатор дл завдання не повинен будти пустим.";
    public static final String PRICE_MIN_NOT_VALID = "Ціна повинна бути більше 1 UAH.";
    public static final String DEVELOPER_NOT_VALID = "У розробника з id (%s) відсутня роль Developer.";
}
