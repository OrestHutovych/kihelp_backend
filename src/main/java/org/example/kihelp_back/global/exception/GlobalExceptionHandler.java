package org.example.kihelp_back.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.argument.exception.ArgumentExistException;
import org.example.kihelp_back.argument.exception.ArgumentNotFoundException;
import org.example.kihelp_back.subject.exception.SubjectExistException;
import org.example.kihelp_back.subject.exception.SubjectNotFoundException;
import org.example.kihelp_back.support.exception.SupportFileLimitException;
import org.example.kihelp_back.task.exception.*;
import org.example.kihelp_back.teacher.exception.TeacherExistException;
import org.example.kihelp_back.teacher.exception.TeacherNotFoundException;
import org.example.kihelp_back.transaction.exception.TransactionExistException;
import org.example.kihelp_back.transaction.exception.TransactionNotFoundException;
import org.example.kihelp_back.user.exception.*;
import org.example.kihelp_back.wallet.exception.*;
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
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleSubjectNotFoundException(SubjectNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TeacherExistException.class)
    public ResponseEntity<Map<String, String>> handleTeacherExistException(TeacherExistException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TeacherNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTeacherNotFoundException(TeacherNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskExistException.class)
    public ResponseEntity<Map<String, String>> handleTaskExistException(TaskExistException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
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
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ArgumentNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleArgumentNotFoundException(ArgumentNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTaskNotFoundException(TaskNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
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
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
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
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WalletExistException.class)
    public ResponseEntity<Map<String, String>> handleWalletExistException(WalletExistException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
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
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
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
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
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
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TelegramException.class)
    public ResponseEntity<Map<String, String>> handleTelegramException(TelegramException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskProcessException.class)
    public ResponseEntity<Map<String, String>> handleTaskProcessException(TaskProcessException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SupportFileLimitException.class)
    public ResponseEntity<Map<String, String>> handleSupportFileLimitException(SupportFileLimitException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<Map<String, String>> handleUserExistException(UserExistException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserRoleNotValidException.class)
    public ResponseEntity<Map<String, String>> handleUserRoleNotValidException(UserRoleNotValidException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MonobankApiException.class)
    public ResponseEntity<Map<String, String>> handleMonobankApiException(MonobankApiException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(MESSAGE_FIELD, ex.getMessage());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
