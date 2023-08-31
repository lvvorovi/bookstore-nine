package com.bookstore.nine.test.util;

import com.bookstore.nine.book.model.BookCreateRequestDto;
import com.bookstore.nine.book.model.BookResponseDto;
import com.bookstore.nine.book.model.BookShortResponseDto;
import com.bookstore.nine.book.model.BookUpdateRequestDto;
import com.bookstore.nine.book.repository.entity.BookEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestUtil {

    public static final String BOOK_NAME = "book name";
    public static final String BOOK_OTHER_NAME = "book other name";
    public static final String BOOK_AUTHOR = "book author";
    public static final String BOOK_DESCRIPTION = "book description";
    public static final String BOOK_ID = "4d98163b-0735-4f5b-908f-93597a30a393";
    public static final String BOOK_OTHER_ID = "4d5a867e-31e5-4106-9d6a-d51afc972312";


    public static BookEntity newBookEntity() {
        var e = new BookEntity();

        e.setId(BOOK_ID);
        e.setName(BOOK_NAME);
        e.setDescription(BOOK_DESCRIPTION);
        e.setAuthor(BOOK_AUTHOR);
        e.setPrice(BigDecimal.TEN);
        e.setCreated(LocalDateTime.now());
        e.setUpdated(LocalDateTime.now());

        return e;
    }

    public static BookEntity newBookEntity(String name) {
        var e = new BookEntity();

        e.setId(BOOK_ID);
        e.setName(name);
        e.setDescription(BOOK_DESCRIPTION);
        e.setAuthor(BOOK_AUTHOR);
        e.setPrice(BigDecimal.TEN);
        e.setCreated(LocalDateTime.now());
        e.setUpdated(LocalDateTime.now());

        return e;
    }

    public static BookEntity newBookEntity(String name, String id) {
        var e = new BookEntity();

        e.setId(id);
        e.setName(name);
        e.setDescription(BOOK_DESCRIPTION);
        e.setAuthor(BOOK_AUTHOR);
        e.setPrice(BigDecimal.TEN);
        e.setCreated(LocalDateTime.now());
        e.setUpdated(LocalDateTime.now());

        return e;
    }


    public static BookCreateRequestDto newBookCreateRequestDto(BookEntity entity) {
        var book = new BookCreateRequestDto();

        book.setName(entity.getName());
        book.setAuthor(entity.getAuthor());
        book.setDescription(entity.getDescription());
        book.setPrice(entity.getPrice());

        return book;
    }

    public static BookUpdateRequestDto newBookUpdateRequestDto(BookEntity entity) {
        var book = new BookUpdateRequestDto();

        book.setId(entity.getId());
        book.setName(entity.getName());
        book.setAuthor(entity.getAuthor());
        book.setDescription(entity.getDescription());
        book.setPrice(entity.getPrice());
        book.setCreated(entity.getCreated());
        book.setUpdated(entity.getUpdated());

        return book;
    }

    public static BookResponseDto newBookResponseDto(BookEntity entity) {
        return new BookResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getAuthor(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getCreated(),
                entity.getUpdated()
        );
    }

    public static BookShortResponseDto newBookShortResponseDto(BookEntity entity) {
        return new BookShortResponseDto(
                entity.getName(),
                entity.getPrice()
        );
    }

}
