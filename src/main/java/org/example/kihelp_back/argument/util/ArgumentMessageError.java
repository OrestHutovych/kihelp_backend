package org.example.kihelp_back.argument.util;

public class ArgumentMessageError {
    private ArgumentMessageError() {}

    public static final String ARG_NAME_NOT_VALID = "Назва аргументу не може бути порожньою.";
    public static final String DESCRIPTION_LENGTH_NOT_VALID = "Довжина опису має бути до 30 символів.";
    public static final String ARGUMENT_EXIST = "Аргумент з назвою %s уже існує.";
    public static final String ARGUMENT_NOT_FOUND = "Аргумент з id %s не знайдено.";
}
