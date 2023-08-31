package com.bookstore.nine.book.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookResponseDto(
        String id,
        String name,
        String author,
        String description,
        BigDecimal price,
        LocalDateTime created,
        LocalDateTime updated
) implements Serializable {
}
