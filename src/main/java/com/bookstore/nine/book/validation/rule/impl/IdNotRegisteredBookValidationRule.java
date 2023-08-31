package com.bookstore.nine.book.validation.rule.impl;

import com.bookstore.nine.book.model.BookUpdateRequestDto;
import com.bookstore.nine.book.repository.BookRepository;
import com.bookstore.nine.book.validation.rule.BookUpdateRequestValidationRule;
import com.bookstore.nine.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1)
public class IdNotRegisteredBookValidationRule implements BookUpdateRequestValidationRule {

    private final BookRepository repository;

    @Override
    public void validate(BookUpdateRequestDto requestDto) {
        var exists = repository.existsById(requestDto.getId());

        if (!exists) {
            throw new BadRequestException("Book with id: %s is not registered");
        }
    }
}
