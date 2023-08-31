package com.bookstore.nine.book.repository;

import com.bookstore.nine.book.model.BookResponseDto;
import com.bookstore.nine.book.model.BookShortResponseDto;
import com.bookstore.nine.book.repository.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, String> {

    @Query("""
            SELECT new com.bookstore.nine.book.model.BookResponseDto(
                b.id, b.name, b.author, b.description, b.price, b.created, b.updated
            )
            FROM BookEntity b
            """)
    Page<BookResponseDto> findAllDto(Pageable pageable);

    @Query("""
            SELECT new com.bookstore.nine.book.model.BookResponseDto(
                b.id, b.name, b.author, b.description, b.price, b.created, b.updated
            )
            FROM BookEntity b
            WHERE
            b.name = :name
            """)
    Optional<BookResponseDto> findDtoByName(@Param("name") String name);

    @Query("""
            SELECT new com.bookstore.nine.book.model.BookShortResponseDto(
                b.name, b,price
            )
            FROM BookEntity b
            """)
    List<BookShortResponseDto> findAllShortDto();

    @Query("""
            SELECT new com.bookstore.nine.book.model.BookShortResponseDto(
                b.name, b,price
            )
            FROM BookEntity b
            WHERE
            b.updated > :since
            OR
            b.created > :since
            """)
    List<BookShortResponseDto> findAllShortDto(@Param("since") LocalDateTime since);

    boolean existsByName(String name);
}
