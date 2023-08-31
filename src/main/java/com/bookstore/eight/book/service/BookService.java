package com.bookstore.eight.book.service;

import com.bookstore.eight.book.mapper.BookMapper;
import com.bookstore.eight.book.model.BookCreateRequestDto;
import com.bookstore.eight.book.model.BookResponseDto;
import com.bookstore.eight.book.repository.BookRepository;
import com.bookstore.eight.book.util.BookUtil;
import com.bookstore.eight.book.validation.BookValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {


    public static final String BOOK_SAVED = "Book saved with Id: %s";
    public static final String BOOK_FOUND = "%s BookEntity found";


    private final BookRepository repository;
    private final BookValidationService validationService;
    private final BookMapper mapper;
    private final BookUtil util;


    @Cacheable("books")
    public Page<BookResponseDto> findAllByPage(Pageable pageable) {
        var dtoPage = repository.findAllDto(pageable);
        log.info(BOOK_FOUND.formatted(dtoPage.getNumberOfElements()));
        return dtoPage;
    }


    @CacheEvict(value = "books", allEntries = true)
    public BookResponseDto save(BookCreateRequestDto requestDto) {
        validationService.validate(requestDto);
        var requestEntity = mapper.toEntity(requestDto);
        util.setValuesOnCreation(requestEntity);
        var savedEntity = repository.save(requestEntity);
        log.info(BOOK_SAVED.formatted(savedEntity.getId()));
        return mapper.toDto(savedEntity);
    }

}
