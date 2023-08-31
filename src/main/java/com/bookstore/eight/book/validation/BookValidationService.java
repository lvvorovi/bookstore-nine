package com.bookstore.eight.book.validation;

import com.bookstore.eight.book.model.BookCreateRequestDto;
import com.bookstore.eight.book.validation.rule.BookCreateRequestValidationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookValidationService {

    private final List<BookCreateRequestValidationRule> createRequestValidationRuleList;

    public void validate(BookCreateRequestDto requestDto) {
        createRequestValidationRuleList.forEach(rule -> rule.validate(requestDto));
    }
}
