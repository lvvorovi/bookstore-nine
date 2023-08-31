package com.bookstore.eight.book.validation.rule.impl;

import com.bookstore.eight.book.model.BookCreateRequestDto;
import com.bookstore.eight.book.repository.BookRepository;
import com.bookstore.eight.book.validation.rule.BookCreateRequestValidationRule;
import com.bookstore.eight.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class NameAlreadyExistsBookCreateValidationRule implements BookCreateRequestValidationRule {

    private final BookRepository repository;

    @Override
    public void validate(BookCreateRequestDto requestDto) {
        var exists = repository.existsByName(requestDto.getName());
        if (exists) {
            throw new BadRequestException("Book with name: %s already exists"
                    .formatted(requestDto.getName()));
        }
    }
}
