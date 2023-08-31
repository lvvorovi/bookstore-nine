package com.bookstore.eight.book.mapper.impl;

import com.bookstore.eight.book.mapper.BookMapper;
import com.bookstore.eight.book.model.BookCreateRequestDto;
import com.bookstore.eight.book.model.BookResponseDto;
import com.bookstore.eight.book.repository.entity.BookEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomBookMapper implements BookMapper {

    @Override
    public BookEntity toEntity(BookCreateRequestDto requestDto) {
        var entity = new BookEntity();
        entity.setName(requestDto.getName());
        entity.setAuthor(requestDto.getAuthor());
        entity.setDescription(requestDto.getDescription());
        return entity;
    }

    @Override
    public BookResponseDto toDto(BookEntity entity) {
        return new BookResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getAuthor(),
                entity.getDescription(),
                entity.getCreated()
        );
    }
}
