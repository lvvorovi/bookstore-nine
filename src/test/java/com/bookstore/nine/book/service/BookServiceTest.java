package com.bookstore.nine.book.service;

import com.bookstore.nine.book.mapper.BookMapper;
import com.bookstore.nine.book.model.BookResponseDto;
import com.bookstore.nine.book.repository.BookRepository;
import com.bookstore.nine.book.util.BookUtil;
import com.bookstore.nine.book.validation.BookValidationService;
import com.bookstore.nine.exception.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.bookstore.nine.test.util.TestUtil.BOOK_ID;
import static com.bookstore.nine.test.util.TestUtil.BOOK_NAME;
import static com.bookstore.nine.test.util.TestUtil.newBookCreateRequestDto;
import static com.bookstore.nine.test.util.TestUtil.newBookEntity;
import static com.bookstore.nine.test.util.TestUtil.newBookResponseDto;
import static com.bookstore.nine.test.util.TestUtil.newBookShortResponseDto;
import static com.bookstore.nine.test.util.TestUtil.newBookUpdateRequestDto;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository repository;
    @Mock
    BookValidationService validationService;
    @Mock
    BookMapper mapper;
    @Mock
    BookUtil util;

    @InjectMocks
    BookService victim;

    @AfterEach
    void wind_down() {
        verifyNoMoreInteractions(repository, validationService, mapper, util);
    }

    @Test
    void save_receive_delegate_return() {
        var entity = newBookEntity();
        var requestDto = newBookCreateRequestDto(entity);
        var expected = newBookResponseDto(entity);
        doNothing().when(validationService).validate(requestDto);
        when(mapper.toEntity(requestDto)).thenReturn(entity);
        doNothing().when(util).setValuesOnCreation(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(expected);

        var actual = victim.save(requestDto);

        assertEquals(expected, actual);
    }

    @Test
    void findAll_delegate_return() {
        var expected = new PageImpl<BookResponseDto>(List.of(newBookResponseDto(newBookEntity())));
        when(repository.findAllDto(any())).thenReturn(expected);

        var actual = victim.findAll(Mockito.any(Pageable.class));

        assertEquals(expected, actual);
    }

    @Test
    void findByName_whenFound_return() {
        var responseDto = newBookResponseDto(newBookEntity());
        var expected = Optional.of(responseDto);
        when(repository.findDtoByName(BOOK_NAME)).thenReturn(expected);

        var actual = victim.findByName(BOOK_NAME);

        assertEquals(expected, actual);
    }

    @Test
    void findByNam_whenNotFound_return() {
        Optional<BookResponseDto> expected = Optional.empty();
        when(repository.findDtoByName(BOOK_NAME)).thenReturn(expected);

        var actual = victim.findByName(BOOK_NAME);

        assertEquals(expected, actual);
    }

    @Test
    void update_delegate_return() {
        var entity = newBookEntity();
        var requestDto = newBookUpdateRequestDto(entity);
        var expected = newBookResponseDto(entity);
        doNothing().when(validationService).validate(requestDto);
        doNothing().when(util).setValuesOnUpdate(requestDto);
        when(mapper.toEntity(requestDto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(expected);

        var actual = victim.update(requestDto);

        assertEquals(expected, actual);
    }

    @Test
    void deleteById_whenFound_delete() {
        when(repository.existsById(BOOK_ID)).thenReturn(true);
        doNothing().when(repository).deleteById(BOOK_ID);

        assertThatNoException().isThrownBy(() -> victim.deleteById(BOOK_ID));
    }

    @Test
    void deleteById_whenNotFound_thenThrowException() {
        when(repository.existsById(BOOK_ID)).thenReturn(false);

        assertThatThrownBy(() -> victim.deleteById(BOOK_ID))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void findAllShortDto_delegate_return() {
        var expected = List.of(newBookShortResponseDto(newBookEntity()));
        when(repository.findAllShortDto()).thenReturn(expected);

        var actual = victim.findAllShortDto();

        assertEquals(expected, actual);
    }

    @Test
    void findAllShortDtoSince_delegate_return() {
        var expected = List.of(newBookShortResponseDto(newBookEntity()));
        when(repository.findAllShortDto(any(LocalDateTime.class))).thenReturn(expected);

        var actual = victim.findAllShortDto(mock(LocalDateTime.class));

        assertEquals(expected, actual);
    }

}