package com.bookstore.nine.book.util;

import com.bookstore.nine.book.model.BookUpdateRequestDto;
import com.bookstore.nine.book.repository.entity.BookEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class BookUtil {

    public void setValuesOnCreation(BookEntity requestEntity) {
        requestEntity.setId(UUID.randomUUID().toString());
        requestEntity.setCreated(LocalDateTime.now());
    }

    public void setValuesOnUpdate(BookUpdateRequestDto requestDto) {
        requestDto.setUpdated(LocalDateTime.now());
    }
}
