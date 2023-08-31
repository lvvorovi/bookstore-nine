package com.bookstore.eight.book.mapper;

import com.bookstore.eight.book.model.BookCreateRequestDto;
import com.bookstore.eight.book.model.BookResponseDto;
import com.bookstore.eight.book.repository.entity.BookEntity;

public interface BookMapper {

    BookEntity toEntity(BookCreateRequestDto requestDto);

    BookResponseDto toDto(BookEntity entity);

}
