package com.bookstore.nine.book.validation.rule.impl;

import com.bookstore.nine.book.repository.BookRepository;
import com.bookstore.nine.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.bookstore.nine.test.util.TestUtil.BOOK_ID;
import static com.bookstore.nine.test.util.TestUtil.BOOK_NAME;
import static com.bookstore.nine.test.util.TestUtil.BOOK_OTHER_ID;
import static com.bookstore.nine.test.util.TestUtil.newBookCreateRequestDto;
import static com.bookstore.nine.test.util.TestUtil.newBookEntity;
import static com.bookstore.nine.test.util.TestUtil.newBookResponseDto;
import static com.bookstore.nine.test.util.TestUtil.newBookUpdateRequestDto;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NameAlreadyExistsBookValidationRuleTest {

    @Mock
    BookRepository repository;

    @InjectMocks
    NameAlreadyExistsBookValidationRule victim;

    @Test
    void validate_CreateRequest_whenExists_throwException() {
        var requestDto = newBookCreateRequestDto(newBookEntity());
        when(repository.existsByName(requestDto.getName())).thenReturn(true);

        assertThatThrownBy(() -> victim.validate(requestDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("already exists");

        verifyNoMoreInteractions(repository);
    }

    @Test
    void validate_CreateRequest_whenDoNotExist_thenDoNothing() {
        var requestDto = newBookCreateRequestDto(newBookEntity());
        when(repository.existsByName(requestDto.getName())).thenReturn(false);

        assertThatNoException().isThrownBy(() -> victim.validate(requestDto));

        verifyNoMoreInteractions(repository);
    }

    @Test
    void validate_UpdateRequest_whenFoundAndSameId_doNothing() {
        var requestDto = newBookUpdateRequestDto(newBookEntity(BOOK_NAME, BOOK_ID));
        var foundDto = newBookResponseDto(newBookEntity(BOOK_NAME, BOOK_ID));
        when(repository.findDtoByName(requestDto.getName())).thenReturn(Optional.of(foundDto));

        assertThatNoException().isThrownBy(() -> victim.validate(requestDto));

        verifyNoMoreInteractions(repository);
    }

    @Test
    void validate_updateRequest_whenFoundAndDifferentId_throwException() {
        var requestDto = newBookUpdateRequestDto(newBookEntity(BOOK_NAME, BOOK_ID));
        var foundDto = newBookResponseDto(newBookEntity(BOOK_NAME, BOOK_OTHER_ID));
        when(repository.findDtoByName(requestDto.getName())).thenReturn(Optional.of(foundDto));

        assertThatThrownBy(() -> victim.validate(requestDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("already exists");
    }


}