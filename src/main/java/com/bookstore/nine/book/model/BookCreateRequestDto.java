package com.bookstore.nine.book.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Data
@Validated
public class BookCreateRequestDto {

    @NotBlank
    @Size(max = 100, message = "name can be maximum 100 characters long")
    private String name;

    @NotBlank
    @Size(max = 100, message = "author name can be maximum 100 characters long")
    private String author;

    @Size(max = 500, message = "description can be maximum 500 characters long")
    private String description;

    @NotNull
    @NumberFormat
    @PositiveOrZero
    private BigDecimal price;

}
