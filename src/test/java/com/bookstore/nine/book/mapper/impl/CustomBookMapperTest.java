package com.bookstore.nine.book.mapper.impl;

import com.bookstore.nine.book.model.BookCreateRequestDto;
import com.bookstore.nine.book.model.BookUpdateRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bookstore.nine.test.util.TestUtil.newBookCreateRequestDto;
import static com.bookstore.nine.test.util.TestUtil.newBookEntity;
import static com.bookstore.nine.test.util.TestUtil.newBookResponseDto;
import static com.bookstore.nine.test.util.TestUtil.newBookUpdateRequestDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class CustomBookMapperTest {

    @InjectMocks
    CustomBookMapper victim;


    @Test
    void toEntity_createRequest() {
        var expected = newBookEntity();
        var requestDto = newBookCreateRequestDto(expected);
        expected.setUpdated(null);
        expected.setCreated(null);
        expected.setId(null);

        var actual = victim.toEntity(requestDto);

        assertEquals(expected, actual);
    }

    @Test
    void toEntity_updateRequest() {
        var expected = newBookEntity();
        var requestDto = newBookUpdateRequestDto(expected);

        var actual = victim.toEntity(requestDto);

        assertEquals(expected, actual);
    }

    @Test
    void toDto() {
        var entity = newBookEntity();
        var expected = newBookResponseDto(entity);

        var actual = victim.toDto(entity);

        assertEquals(expected, actual);
    }

    @Test
    void toEntity_createRequest_useAllFields() {
        var requestMock = mock(BookCreateRequestDto.class);

        victim.toEntity(requestMock);

        verify(requestMock, times(1)).getName();
        verify(requestMock, times(1)).getAuthor();
        verify(requestMock, times(1)).getDescription();
        verify(requestMock, times(1)).getPrice();
        verifyNoMoreInteractions(requestMock);
    }

    @Test
    void toEntity_updateRequest_useAllFields() {
        var requestMock = mock(BookUpdateRequestDto.class);

        victim.toEntity(requestMock);

        verify(requestMock, times(1)).getName();
        verify(requestMock, times(1)).getAuthor();
        verify(requestMock, times(1)).getDescription();
        verify(requestMock, times(1)).getPrice();
        verify(requestMock, times(1)).getUpdated();
        verify(requestMock, times(1)).getCreated();
        verify(requestMock, times(1)).getId();
        verifyNoMoreInteractions(requestMock);
    }

}