package org.example.kihelp_back.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.argument.exception.ArgumentExistException;
import org.example.kihelp_back.argument.exception.ArgumentNotFoundException;
import org.example.kihelp_back.subject.exception.SubjectExistException;
import org.example.kihelp_back.subject.exception.SubjectNotFoundException;
import org.example.kihelp_back.task.exception.TaskDeveloperNotValidException;
import org.example.kihelp_back.task.exception.TaskExistException;
import org.example.kihelp_back.task.exception.TaskNotFoundException;
import org.example.kihelp_back.task.exception.TypeNotValidException;
import org.example.kihelp_back.teacher.exception.TeacherExistException;
import org.example.kihelp_back.teacher.exception.TeacherNotFoundException;
import org.example.kihelp_back.user.exception.RoleNotFoundException;
import org.example.kihelp_back.user.exception.UserIsBannedException;
import org.example.kihelp_back.user.exception.UserNotFoundException;
import org.example.kihelp_back.user.exception.UserUnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = "message";
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SubjectExistException.class)
    public ResponseEntity<Map<String, String>> handleSubjectExistException(SubjectExistException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleSubjectNotFoundException(SubjectNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TeacherExistException.class)
    public ResponseEntity<Map<String, String>> handleTeacherExistException(TeacherExistException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TeacherNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTeacherNotFoundException(TeacherNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskExistException.class)
    public ResponseEntity<Map<String, String>> handleTaskExistException(TaskExistException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TypeNotValidException.class)
    public ResponseEntity<Map<String, String>> handleTypeNotValidException(TypeNotValidException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ArgumentExistException.class)
    public ResponseEntity<Map<String, String>> handleArgumentExistException(ArgumentExistException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ArgumentNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleArgumentNotFoundException(ArgumentNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTaskNotFoundException(TaskNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIsBannedException.class)
    public ResponseEntity<Map<String, String>> handleUserIsBannedException(UserIsBannedException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRoleNotFoundException(RoleNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<Map<String, String>> handleUserUnauthorizedException(UserUnauthorizedException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TaskDeveloperNotValidException.class)
    public ResponseEntity<Map<String, String>> handleTaskDeveloperNotValidException(TaskDeveloperNotValidException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
