package com.bookstore.nine.book.controller;

import com.bookstore.nine.book.model.BookShortResponseDto;
import com.bookstore.nine.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@Validated
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_books_read')")
public class BookController implements BookControllerContract {

    private final BookService service;

    @GetMapping
    public ResponseEntity<List<BookShortResponseDto>> findAll(@RequestParam(required = false) LocalDateTime since) {
        List<BookShortResponseDto> respoDtoList;

        if (since == null) {
            respoDtoList = service.findAllShortDto();
        } else {
            respoDtoList = service.findAllShortDto(since);
        }

        if (respoDtoList.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .ok(respoDtoList);
    }
}
