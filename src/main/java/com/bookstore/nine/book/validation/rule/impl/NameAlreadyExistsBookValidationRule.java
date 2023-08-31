package com.bookstore.nine.book.validation.rule.impl;

import com.bookstore.nine.book.model.BookCreateRequestDto;
import com.bookstore.nine.book.model.BookUpdateRequestDto;
import com.bookstore.nine.book.repository.BookRepository;
import com.bookstore.nine.book.validation.rule.BookCreateRequestValidationRule;
import com.bookstore.nine.book.validation.rule.BookUpdateRequestValidationRule;
import com.bookstore.nine.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
@RequiredArgsConstructor
public class NameAlreadyExistsBookValidationRule implements BookCreateRequestValidationRule, BookUpdateRequestValidationRule {

    private final BookRepository repository;

    private static BadRequestException getBadRequestException(String name) {
        return new BadRequestException("Book with name: %s already exists"
                .formatted(name));
    }

    @Override
    public void validate(BookCreateRequestDto requestDto) {
        var exists = repository.existsByName(requestDto.getName());
        if (exists) {
            throw getBadRequestException(requestDto.getName());
        }
    }

    @Override
    public void validate(BookUpdateRequestDto requestDto) {
        var optional = repository.findDtoByName(requestDto.getName());

        optional.ifPresent(book -> {
            var otherBookId = !requestDto.getId().equals(book.id());
            if (otherBookId) {
                throw getBadRequestException(requestDto.getName());
            }
        });
    }
}
