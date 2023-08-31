package com.bookstore.eight.book.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public record BookResponseDto(
        String id,
        String name,
        String author,
        String description,
        LocalDateTime created
) implements Serializable {
}
