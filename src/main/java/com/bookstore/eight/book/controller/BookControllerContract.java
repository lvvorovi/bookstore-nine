package com.bookstore.eight.book.controller;


import com.bookstore.eight.book.model.BookResponseDto;
import com.bookstore.eight.exception.ErrorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public interface BookControllerContract {

    @Operation(summary = "Get a list of books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "books found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookResponseDto.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDto.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "bad request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDto.class))
                    }
            )
    })
    ResponseEntity<Page<BookResponseDto>> findAllByPage(@ParameterObject Pageable pageable);
}
