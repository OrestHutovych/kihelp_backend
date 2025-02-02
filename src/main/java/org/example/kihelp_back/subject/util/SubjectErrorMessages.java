package org.example.kihelp_back.subject.util;

public class SubjectErrorMessages {
    private SubjectErrorMessages() {}

    public static final String SUBJECT_EXIST = "Предмет з назвою %s для курсу %s вже існує!";
    public static final String COURSE_VALUE = "Обраний курс має бути в діапазоні від 1 до 4.";
    public static final String SUBJECT_NAME_NOT_VALID = "Назва предмета не повинна бути порожньою.";
    public static final String SUBJECT_NOT_FOUND = "Предмет з id %s не знайдено.";
}
