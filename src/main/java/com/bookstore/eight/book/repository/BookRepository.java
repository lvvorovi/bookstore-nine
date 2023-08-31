package com.bookstore.eight.book.repository;

import com.bookstore.eight.book.model.BookResponseDto;
import com.bookstore.eight.book.repository.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<BookEntity, String> {

    @Query("""
            SELECT new com.bookstore.eight.book.model.BookResponseDto(
                b.id, b.name, b.author, b.description, b.created
            )
            FROM BookEntity b
            """)
    Page<BookResponseDto> findAllDto(Pageable pageable);

    boolean existsByName(String name);
}
