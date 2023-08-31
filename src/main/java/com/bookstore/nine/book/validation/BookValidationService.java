package com.bookstore.nine.book.validation;

import com.bookstore.nine.book.model.BookCreateRequestDto;
import com.bookstore.nine.book.model.BookUpdateRequestDto;
import com.bookstore.nine.book.validation.rule.BookCreateRequestValidationRule;
import com.bookstore.nine.book.validation.rule.BookUpdateRequestValidationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookValidationService {

    private final List<BookCreateRequestValidationRule> createRequestValidationRuleList;
    private final List<BookUpdateRequestValidationRule> updateRequestValidationRuleList;

    public void validate(BookCreateRequestDto requestDto) {
        createRequestValidationRuleList.forEach(rule -> rule.validate(requestDto));
    }

    public void validate(BookUpdateRequestDto requestDto) {
        updateRequestValidationRuleList.forEach(rule -> rule.validate(requestDto));
    }
}
