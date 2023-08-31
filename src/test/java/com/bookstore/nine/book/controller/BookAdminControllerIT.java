package com.bookstore.nine.book.controller;

import com.bookstore.nine.book.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.bookstore.nine.test.util.JsonTestUtil.bookCreateRequestToJson;
import static com.bookstore.nine.test.util.JsonTestUtil.jsonToBookResponseDto;
import static com.bookstore.nine.test.util.TestUtil.newBookCreateRequestDto;
import static com.bookstore.nine.test.util.TestUtil.newBookEntity;
import static com.bookstore.nine.test.util.TestUtil.newBookUpdateRequestDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class BookAdminControllerIT {


    @Autowired
    BookRepository repository;
    @Autowired
    MockMvc mvc;

    @AfterEach
    void windDown() {
        repository.deleteAll();
    }

    @Test
    void save_whenNoAuth_then403() throws Exception {
        var requestDto = newBookCreateRequestDto(newBookEntity());

        mvc.perform(post("http://localhost/api/v1/admin/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookCreateRequestToJson(requestDto)))
                .andExpect(status().is(403));
    }

    @Test
    void findAllPaged_whenNoAuth_then401() throws Exception {
        mvc.perform(get("http://localhost/api/v1/admin/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    void findAllByName_whenNoAuth_then401() throws Exception {
        mvc.perform(get("http://localhost/api/v1/admin/books/name/name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    void update_whenNoAuth_then403() throws Exception {
        var requestDto = newBookUpdateRequestDto(newBookEntity());

        mvc.perform(put("http://localhost/api/v1/admin/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookCreateRequestToJson(requestDto)))
                .andExpect(status().is(403));
    }

    @Test
    @DirtiesContext
    void save_whenAuth_thenSave_andReturn() throws Exception {
        var requestDto = newBookCreateRequestDto(newBookEntity());

        var result = mvc.perform(post("http://localhost/api/v1/admin/books")
                        .with(jwt()
                                .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookCreateRequestToJson(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        var content = result.getResponse().getContentAsString();
        var body = jsonToBookResponseDto(content);

        assertThat(body.id()).isNotBlank();
    }

    @Test
    @DirtiesContext
    void save_whenAuth_andNameAlreadyExists_then400() throws Exception {
        var entity = newBookEntity();
        var requestDto = newBookCreateRequestDto(entity);
        repository.save(entity);

        mvc.perform(post("http://localhost/api/v1/admin/books")
                        .with(jwt()
                                .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookCreateRequestToJson(requestDto)))
                .andExpect(status().isBadRequest());


    }

    @Test
    @DirtiesContext
    void update_whenAuth_thenUpdate_andReturn() throws Exception {
        var entity = newBookEntity();
        var requestDto = newBookUpdateRequestDto(entity);

        repository.save(entity);

        var result = mvc.perform(put("http://localhost/api/v1/admin/books")
                        .with(jwt()
                                .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookCreateRequestToJson(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        var content = result.getResponse().getContentAsString();
        var body = jsonToBookResponseDto(content);

        assertThat(body.id()).isNotBlank();
    }


}