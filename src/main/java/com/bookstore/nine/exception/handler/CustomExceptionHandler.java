package com.bookstore.nine.exception.handler;

import com.bookstore.nine.exception.BadRequestException;
import com.bookstore.nine.exception.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDto> handleBadRequestException(BadRequestException ex) {
        var errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, ex.getClass().getSimpleName(), ex.getMessage());
        log.info(errorDto.toString());

        return ResponseEntity
                .badRequest()
                .body(errorDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleBadRequestException(MethodArgumentNotValidException ex) {
        var message = ex.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        var errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, ex.getClass().getSimpleName(), message);
        log.info(errorDto.toString());

        return ResponseEntity
                .badRequest()
                .body(errorDto);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedException(AccessDeniedException ex) {
        var errorDto = new ErrorDto(HttpStatus.FORBIDDEN, ex.getClass().getSimpleName(), ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(errorDto);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDto> handleBRuntimeException(RuntimeException ex) {
        log.error(Arrays.toString(ex.getStackTrace()));
        ex.printStackTrace();
        var errorDto = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, ex.getClass().getSimpleName(), ex.getMessage());

        return ResponseEntity
                .internalServerError()
                .body(errorDto);
    }


}
