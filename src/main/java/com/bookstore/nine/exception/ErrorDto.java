package com.bookstore.nine.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorDto {

    private Integer code;
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ErrorDto(HttpStatus status, String error, String message) {
        this.code = status.value();
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
