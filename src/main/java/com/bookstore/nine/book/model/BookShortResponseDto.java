package com.bookstore.nine.book.model;

import java.math.BigDecimal;

public record BookShortResponseDto(
        String name,
        BigDecimal price
) {
}
