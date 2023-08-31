package com.bookstore.eight.book.validation.rule;

import com.bookstore.eight.book.model.BookCreateRequestDto;

public interface BookCreateRequestValidationRule {
    void validate(BookCreateRequestDto requestDto);
}
