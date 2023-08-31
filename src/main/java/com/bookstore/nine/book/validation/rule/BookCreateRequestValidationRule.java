package com.bookstore.nine.book.validation.rule;

import com.bookstore.nine.book.model.BookCreateRequestDto;

public interface BookCreateRequestValidationRule {
    void validate(BookCreateRequestDto requestDto);
}
