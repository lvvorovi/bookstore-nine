package com.bookstore.nine.book.controller;

import com.bookstore.nine.book.model.BookCreateRequestDto;
import com.bookstore.nine.book.model.BookResponseDto;
import com.bookstore.nine.book.model.BookUpdateRequestDto;
import com.bookstore.nine.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/api/v1/admin/books")
@Validated
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
public class BookAdminController {


    private final BookService service;


    @PostMapping
    public ResponseEntity<BookResponseDto> save(@RequestBody @Valid BookCreateRequestDto requestDto) {
        var responseDto = service.save(requestDto);

        return ResponseEntity
                .ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<Page<BookResponseDto>> findAllPaged(@SortDefault(sort = "created", direction = DESC)
                                                              @PageableDefault(size = 50)
                                                              @RequestParam Pageable pageable) {
        var responseDtoPage = service.findAll(pageable);

        return ResponseEntity
                .ok(responseDtoPage);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<BookResponseDto> findByName(@PathVariable String name) {
        var responseDto = service.findByName(name);

        return responseDto.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity
                        .noContent()
                        .build());
    }

    @PutMapping
    public ResponseEntity<BookResponseDto> update(@RequestBody @Valid BookUpdateRequestDto requestDto) {
        var responseDto = service.update(requestDto);

        return ResponseEntity
                .ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        service.deleteById(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
