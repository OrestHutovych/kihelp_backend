package org.example.kihelp_back.task.exception;

public class TaskExistException extends RuntimeException {
    public TaskExistException(String message) {
        super(message);
    }
}
