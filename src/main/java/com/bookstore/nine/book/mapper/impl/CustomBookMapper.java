package com.bookstore.nine.book.mapper.impl;

import com.bookstore.nine.book.mapper.BookMapper;
import com.bookstore.nine.book.model.BookCreateRequestDto;
import com.bookstore.nine.book.model.BookResponseDto;
import com.bookstore.nine.book.model.BookUpdateRequestDto;
import com.bookstore.nine.book.repository.entity.BookEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomBookMapper implements BookMapper {

    @Override
    public BookEntity toEntity(BookCreateRequestDto requestDto) {
        var entity = new BookEntity();

        entity.setName(requestDto.getName());
        entity.setAuthor(requestDto.getAuthor());
        entity.setDescription(requestDto.getDescription());
        entity.setPrice(requestDto.getPrice());

        return entity;
    }

    @Override
    public BookEntity toEntity(BookUpdateRequestDto requestDto) {
        var entity = new BookEntity();

        entity.setId(requestDto.getId());
        entity.setName(requestDto.getName());
        entity.setAuthor(requestDto.getAuthor());
        entity.setDescription(requestDto.getDescription());
        entity.setPrice(requestDto.getPrice());
        entity.setCreated(requestDto.getCreated());
        entity.setUpdated(requestDto.getUpdated());

        return entity;
    }

    @Override
    public BookResponseDto toDto(BookEntity entity) {
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
}
