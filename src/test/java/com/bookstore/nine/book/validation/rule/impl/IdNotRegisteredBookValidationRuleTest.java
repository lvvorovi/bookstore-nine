package com.bookstore.nine.book.validation.rule.impl;

import com.bookstore.nine.book.repository.BookRepository;
import com.bookstore.nine.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bookstore.nine.test.util.TestUtil.newBookEntity;
import static com.bookstore.nine.test.util.TestUtil.newBookUpdateRequestDto;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IdNotRegisteredBookValidationRuleTest {

    @Mock
    BookRepository repository;

    @InjectMocks
    IdNotRegisteredBookValidationRule victim;

    @Test
    void validate_whenExists_doNothing() {
        var requestDto = newBookUpdateRequestDto(newBookEntity());
        when(repository.existsById(requestDto.getId())).thenReturn(true);

        assertThatNoException().isThrownBy(() -> victim.validate(requestDto));
    }

    @Test
    void validate_whenDoNoExist_thenThrowException() {
        var requestDto = newBookUpdateRequestDto(newBookEntity());
        when(repository.existsById(requestDto.getId())).thenReturn(false);

        assertThatThrownBy(() -> victim.validate(requestDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("not registered");
    }
}