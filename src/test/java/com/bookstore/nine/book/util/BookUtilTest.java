package com.bookstore.nine.book.util;

import com.bookstore.nine.book.model.BookUpdateRequestDto;
import com.bookstore.nine.book.repository.entity.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class BookUtilTest {

    @InjectMocks
    BookUtil victim;

    @Test
    void setValuesOnCreation() {
        var mock = mock(BookEntity.class);

        victim.setValuesOnCreation(mock);

        verify(mock, times(1)).setId(anyString());
        verify(mock, times(1)).setCreated(any());
        verifyNoMoreInteractions(mock);
    }

    @Test
    void setValuesOnUpdate() {
        var mock = mock(BookUpdateRequestDto.class);

        victim.setValuesOnUpdate(mock);

        verify(mock, times(1)).setUpdated(any());
        verifyNoMoreInteractions(mock);
    }

}