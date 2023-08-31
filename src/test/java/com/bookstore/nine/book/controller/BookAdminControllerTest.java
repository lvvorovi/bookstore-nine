package com.bookstore.nine.book.controller;

import com.bookstore.nine.book.service.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static com.bookstore.nine.test.util.TestUtil.BOOK_ID;
import static com.bookstore.nine.test.util.TestUtil.BOOK_NAME;
import static com.bookstore.nine.test.util.TestUtil.newBookCreateRequestDto;
import static com.bookstore.nine.test.util.TestUtil.newBookEntity;
import static com.bookstore.nine.test.util.TestUtil.newBookResponseDto;
import static com.bookstore.nine.test.util.TestUtil.newBookUpdateRequestDto;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookAdminControllerTest {

    @Mock
    BookService service;

    @InjectMocks
    BookAdminController victim;

    @AfterEach
    void windDown() {
        verifyNoMoreInteractions(service);
    }

    @Test
    void save() {
        var entity = newBookEntity();
        var requestDto = newBookCreateRequestDto(entity);
        var responseDto = newBookResponseDto(entity);
        var expected = ResponseEntity.ok(responseDto);

        when(service.save(requestDto)).thenReturn(responseDto);

        var actual = victim.save(requestDto);

        assertEquals(expected, actual);
    }

    @Test
    void findAllPaged() {
        var responseDtoPage = new PageImpl<>(List.of(newBookResponseDto(newBookEntity())));
        var expected = ResponseEntity.ok(responseDtoPage);
        when(service.findAll(any(Pageable.class))).thenReturn(responseDtoPage);

        var actual = victim.findAllPaged(mock(Pageable.class));

        assertEquals(expected, actual);
    }

    @Test
    void findByName_whenFound_return_andStatusOK() {
        var responseDto = newBookResponseDto(newBookEntity());
        var expected = ResponseEntity.ok(responseDto);
        when(service.findByName(BOOK_NAME)).thenReturn(Optional.of(responseDto));

        var actual = victim.findByName(BOOK_NAME);

        assertEquals(expected, actual);
    }

    @Test
    void findByName_whenNotFound_return_andStatusNoContent() {
        var expected = ResponseEntity.noContent().build();
        when(service.findByName(BOOK_NAME)).thenReturn(Optional.empty());

        var actual = victim.findByName(BOOK_NAME);

        assertEquals(expected, actual);
    }

    @Test
    void update() {
        var entity = newBookEntity();
        var requestDto = newBookUpdateRequestDto(entity);
        var responseDto = newBookResponseDto(entity);
        var expected = ResponseEntity.ok(responseDto);
        when(service.update(requestDto)).thenReturn(responseDto);

        var actual = victim.update(requestDto);

        assertEquals(expected, actual);
    }

    @Test
    void deleteById() {
        doNothing().when(service).deleteById(BOOK_ID);

        assertThatNoException().isThrownBy(() -> victim.deleteById(BOOK_ID));
    }


}