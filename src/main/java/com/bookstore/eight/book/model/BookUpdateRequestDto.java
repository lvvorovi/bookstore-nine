package com.bookstore.eight.book.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BookUpdateRequestDto extends BookCreateRequestDto {

    @NotBlank
    @Size(min = 36, max = 36)
    private String id;

}
