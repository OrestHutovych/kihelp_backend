package org.example.kihelp_back.teacher.util;

public class ErrorMessage {
    private ErrorMessage() {}

    public static final String TEACHER_NAME_NOT_BLANK = "Ініціали викладача не повинні бути порожніми.";
    public static final String TEACHER_ALREADY_EXIST = "Викладач з іменем %s вже існує для предмету %s.";
    public static final String TEACHER_NOT_FOUND = "Викладач з id %s не знайдено.";
}
