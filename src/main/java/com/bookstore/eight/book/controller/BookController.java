package com.bookstore.eight.book.controller;

import com.bookstore.eight.book.model.BookCreateRequestDto;
import com.bookstore.eight.book.model.BookResponseDto;
import com.bookstore.eight.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/books")
@Validated
@RequiredArgsConstructor
public class BookController implements BookControllerContract {

    private final BookService service;

    @GetMapping
    @Override
    public ResponseEntity<Page<BookResponseDto>> findAllByPage(@SortDefault(sort = "created", direction = DESC)
                                                               @PageableDefault(size = 50)
                                                               @ParameterObject Pageable pageable) {
        var resposeDtoPage = service.findAllByPage(pageable);

        return ResponseEntity
                .ok(resposeDtoPage);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookResponseDto> save(@RequestBody @Valid BookCreateRequestDto requestDto) {
        var responseDto = service.save(requestDto);

        return ResponseEntity
                .ok(responseDto);
    }
}
