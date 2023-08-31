package com.bookstore.nine.book.controller;

import com.bookstore.nine.book.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class BookControllerIT {

    @Autowired
    BookRepository repository;
    @Autowired
    MockMvc mvc;

    @AfterEach
    void windDown() {
        repository.deleteAll();
    }

    @Test
    void findAll_whenNoAuth_then401() throws Exception {
        mvc.perform(get("http://localhost/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

}