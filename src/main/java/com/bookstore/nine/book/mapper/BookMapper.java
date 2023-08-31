package com.bookstore.nine.book.mapper;

import com.bookstore.nine.book.model.BookCreateRequestDto;
import com.bookstore.nine.book.model.BookResponseDto;
import com.bookstore.nine.book.model.BookUpdateRequestDto;
import com.bookstore.nine.book.repository.entity.BookEntity;

public interface BookMapper {

    BookEntity toEntity(BookCreateRequestDto requestDto);

    BookEntity toEntity(BookUpdateRequestDto requestDto);

    BookResponseDto toDto(BookEntity entity);

}
