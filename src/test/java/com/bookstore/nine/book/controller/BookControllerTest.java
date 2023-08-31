package com.bookstore.nine.book.controller;

import com.bookstore.nine.book.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static com.bookstore.nine.test.util.TestUtil.newBookEntity;
import static com.bookstore.nine.test.util.TestUtil.newBookShortResponseDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    BookService service;

    @InjectMocks
    BookController victim;

    @Test
    void findAll_whenNoParameter_andFound_thenReturnOK() {
        var responseDtoList = List.of(newBookShortResponseDto(newBookEntity()));
        var expected = ResponseEntity.ok(responseDtoList);
        when(service.findAllShortDto()).thenReturn(responseDtoList);

        var actual = victim.findAll(null);

        assertEquals(expected, actual);
    }

    @Test
    void findAll_whenNoParameter_andNotFound_thenReturnNoContent() {
        var expected = ResponseEntity.noContent().build();
        when(service.findAllShortDto()).thenReturn(List.of());

        var actual = victim.findAll(null);

        assertEquals(expected, actual);
    }

    @Test
    void findAll_whenParameter_andFound_thenReturnOK() {
        var since = LocalDateTime.now();
        var responseDtoList = List.of(newBookShortResponseDto(newBookEntity()));
        var expected = ResponseEntity.ok(responseDtoList);
        when(service.findAllShortDto(since)).thenReturn(responseDtoList);

        var actual = victim.findAll(since);

        assertEquals(expected, actual);
    }

    @Test
    void findAll_whenParameter_andNotFound_thenReturnNoContent() {
        var since = LocalDateTime.now();
        var expected = ResponseEntity.noContent().build();
        when(service.findAllShortDto(since)).thenReturn(List.of());

        var actual = victim.findAll(since);

        assertEquals(expected, actual);
    }

}