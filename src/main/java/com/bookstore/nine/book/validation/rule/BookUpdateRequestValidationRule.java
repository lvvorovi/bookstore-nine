package com.bookstore.nine.book.validation.rule;

import com.bookstore.nine.book.model.BookUpdateRequestDto;

public interface BookUpdateRequestValidationRule {
    void validate(BookUpdateRequestDto requestDto);
}
