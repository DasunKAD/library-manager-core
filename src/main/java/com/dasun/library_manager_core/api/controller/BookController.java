package com.dasun.library_manager_core.api.controller;

import com.dasun.library_manager_core.api.dto.BookDto;
import com.dasun.library_manager_core.api.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("")
    public ResponseEntity<Page<BookDto>> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<BookDto> books = bookService.getAllBooks(page, size);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<BookDto> registerNewBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.addBook(bookDto), HttpStatus.CREATED);
    }
}
