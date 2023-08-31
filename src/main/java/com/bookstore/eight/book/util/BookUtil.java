package com.bookstore.eight.book.util;

import com.bookstore.eight.book.repository.entity.BookEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class BookUtil {

    public void setValuesOnCreation(BookEntity requestEntity) {
        requestEntity.setId(UUID.randomUUID().toString());
        requestEntity.setCreated(LocalDateTime.now());
    }

}
