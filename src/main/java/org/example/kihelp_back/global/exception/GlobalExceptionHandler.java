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
import org.example.kihelp_back.transaction.exception.TransactionExistException;
import org.example.kihelp_back.transaction.exception.TransactionNotFoundException;
import org.example.kihelp_back.transaction.exception.TransactionStatusNotFoundException;
import org.example.kihelp_back.transaction.exception.TransactionTypeNotAllowedException;
import org.example.kihelp_back.user.exception.*;
import org.example.kihelp_back.wallet.exception.*;
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
    private static final String MESSAGE_FIELD = "message";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errors.put(MESSAGE_FIELD, errorMessage);
        });
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SubjectExistException.class)
    public ResponseEntity<Map<String, String>> handleSubjectExistException(SubjectExistException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleSubjectNotFoundException(SubjectNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TeacherExistException.class)
    public ResponseEntity<Map<String, String>> handleTeacherExistException(TeacherExistException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TeacherNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTeacherNotFoundException(TeacherNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskExistException.class)
    public ResponseEntity<Map<String, String>> handleTaskExistException(TaskExistException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TypeNotValidException.class)
    public ResponseEntity<Map<String, String>> handleTypeNotValidException(TypeNotValidException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ArgumentExistException.class)
    public ResponseEntity<Map<String, String>> handleArgumentExistException(ArgumentExistException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ArgumentNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleArgumentNotFoundException(ArgumentNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTaskNotFoundException(TaskNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIsBannedException.class)
    public ResponseEntity<Map<String, String>> handleUserIsBannedException(UserIsBannedException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRoleNotFoundException(RoleNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<Map<String, String>> handleUserUnauthorizedException(UserUnauthorizedException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TaskDeveloperNotValidException.class)
    public ResponseEntity<Map<String, String>> handleTaskDeveloperNotValidException(TaskDeveloperNotValidException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WalletDefaultExistException.class)
    public ResponseEntity<Map<String, String>> handleWalletDefaultExistException(WalletDefaultExistException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WalletExistException.class)
    public ResponseEntity<Map<String, String>> handleWalletExistException(WalletExistException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WalletIsNotDefaultException.class)
    public ResponseEntity<Map<String, String>> handleWalletIsNotDefaultException(WalletIsNotDefaultException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleWalletNotFoundException(WalletNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalRoleChangeException.class)
    public ResponseEntity<Map<String, String>> handleIllegalRoleChangeException(IllegalRoleChangeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionExistException.class)
    public ResponseEntity<Map<String, String>> handleTransactionExistException(TransactionExistException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WalletAmountNotValidException.class)
    public ResponseEntity<Map<String, String>> handleWalletAmountNotValidException(WalletAmountNotValidException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTransactionNotFoundException(TransactionNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionStatusNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTransactionStatusNotFoundException(TransactionStatusNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionTypeNotAllowedException.class)
    public ResponseEntity<Map<String, String>> handleTransactionTypeNotAllowedException(TransactionTypeNotAllowedException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TelegramException.class)
    public ResponseEntity<Map<String, String>> handleTelegramException(TelegramException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
