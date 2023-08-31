package com.bookstore.nine.book.controller;


import com.bookstore.nine.book.model.BookCreateRequestDto;
import com.bookstore.nine.book.model.BookResponseDto;
import com.bookstore.nine.book.model.BookUpdateRequestDto;
import com.bookstore.nine.exception.ErrorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.data.domain.Sort.Direction.DESC;

public interface BookAdminControllerContract {

    @Operation(summary = """
            Saves Resource in DB. Returns saved resource.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "resource found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookResponseDto.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "bad request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDto.class))
                    }
            )
    })
    ResponseEntity<BookResponseDto> save(@RequestBody @Valid BookCreateRequestDto requestDto);


    @Operation(summary = """
            Returns page of requested resources. If pageable is not supplier default values will be used.
            Defaults page size - 50. Default page - 0. Degault sort - 'created,desc'
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "resource found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookResponseDto.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "bad request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDto.class))
                    }
            )
    })
    ResponseEntity<Page<BookResponseDto>> findAllPaged(@SortDefault(sort = "created", direction = DESC)
                                                       @PageableDefault(size = 50)
                                                       @RequestParam Pageable pageable);


    @Operation(summary = """
            Returns requested resource by name.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "resource found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookResponseDto.class))
                    }
            ),
            @ApiResponse(responseCode = "204", description = "resource not found"),
            @ApiResponse(responseCode = "400", description = "bad request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDto.class))
                    }
            )
    })
    ResponseEntity<BookResponseDto> findByName(@PathVariable String name);


    @Operation(summary = """
            Updates resource in db. Returns updated resource.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "resource updated",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookResponseDto.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "bad request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDto.class))
                    }
            )
    })
    ResponseEntity<BookResponseDto> update(@RequestBody @Valid BookUpdateRequestDto requestDto);


    @Operation(summary = """
            Deletes resource in db by id.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "resource deleted"),
            @ApiResponse(responseCode = "400", description = "bad request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDto.class))
                    }
            )
    })
    ResponseEntity<Void> deleteById(@PathVariable String id);


}
