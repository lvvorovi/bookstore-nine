package com.bookstore.nine.book.controller;


import com.bookstore.nine.book.model.BookShortResponseDto;
import com.bookstore.nine.exception.ErrorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

public interface BookControllerContract {

    @Operation(summary = """
            Returns a list of books after the specified in parameter `since`.
            If parameter `since` is not supplied - returns list of all books
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "books found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookShortResponseDto.class))
                    }
            ),
            @ApiResponse(responseCode = "204", description = "success, books not found"),
            @ApiResponse(responseCode = "400", description = "bad request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDto.class))
                    }
            )
    })
    ResponseEntity<List<BookShortResponseDto>> findAll(@RequestParam(required = false) LocalDateTime since);
}
