package com.bookstore.nine.book.service;

import com.bookstore.nine.book.mapper.BookMapper;
import com.bookstore.nine.book.model.BookCreateRequestDto;
import com.bookstore.nine.book.model.BookResponseDto;
import com.bookstore.nine.book.model.BookShortResponseDto;
import com.bookstore.nine.book.model.BookUpdateRequestDto;
import com.bookstore.nine.book.repository.BookRepository;
import com.bookstore.nine.book.util.BookUtil;
import com.bookstore.nine.book.validation.BookValidationService;
import com.bookstore.nine.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {


    public static final String BOOK_SAVED = "Book saved with Id: %s";
    public static final String BOOK_UPDATED = "Book updated with Id: %s";
    public static final String BOOK_DELETED = "Book deleted with Id: %s";
    public static final String BOOKS_FOUND = "%s BookEntity found";
    public static final String BOOK_NOT_FOUND_ID = "BookEntity not found with id: %s";


    private final BookRepository repository;
    private final BookValidationService validationService;
    private final BookMapper mapper;
    private final BookUtil util;


    @CacheEvict(value = "books", allEntries = true)
    public BookResponseDto save(BookCreateRequestDto requestDto) {
        validationService.validate(requestDto);
        var requestEntity = mapper.toEntity(requestDto);
        util.setValuesOnCreation(requestEntity);
        var savedEntity = repository.save(requestEntity);
        log.info(BOOK_SAVED.formatted(savedEntity.getId()));
        return mapper.toDto(savedEntity);
    }


    @Cacheable("books")
    public Page<BookResponseDto> findAll(Pageable pageable) {
        var dtoList = repository.findAllDto(pageable);
        log.info(BOOKS_FOUND.formatted(dtoList.getNumberOfElements()));
        return dtoList;
    }

    @Cacheable("books")
    public Optional<BookResponseDto> findByName(String name) {
        var optional = repository.findDtoByName(name);
        optional.ifPresent(book -> log.info("BookEntity with id: %s found".formatted(book.id())));
        return optional;
    }


    @CacheEvict(value = "books", allEntries = true)
    public BookResponseDto update(BookUpdateRequestDto requestDto) {
        validationService.validate(requestDto);
        util.setValuesOnUpdate(requestDto);
        var requestEntity = mapper.toEntity(requestDto);
        var savedEntity = repository.save(requestEntity);
        log.info(BOOK_UPDATED.formatted(savedEntity.getId()));
        return mapper.toDto(savedEntity);
    }


    @CacheEvict(value = "books", allEntries = true)
    public void deleteById(String id) {
        var exists = repository.existsById(id);

        if (exists) {
            repository.deleteById(id);
            log.info(BOOK_DELETED.formatted(id));
        } else {
            throw new BadRequestException(BOOK_NOT_FOUND_ID.formatted(id));
        }
    }


    public List<BookShortResponseDto> findAllShortDto() {
        var dtoList = repository.findAllShortDto();
        log.info(BOOKS_FOUND.formatted(dtoList.size()));
        return dtoList;
    }

    public List<BookShortResponseDto> findAllShortDto(LocalDateTime since) {
        var dtoList = repository.findAllShortDto(since);
        log.info(BOOKS_FOUND.formatted(dtoList.size()));
        return dtoList;
    }

}
