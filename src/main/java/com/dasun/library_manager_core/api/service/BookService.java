package com.dasun.library_manager_core.api.service;

import com.dasun.library_manager_core.api.dto.BookDto;
import com.dasun.library_manager_core.api.dto.BorrowerDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    BookDto addBook(BookDto bookDto);
    BookDto updateBook(BookDto bookDto);
    void deleteBook(Long bookId);
    Page<BookDto> getAllBooks(int page, int size);
    List<BookDto> getBooksByAvailability(boolean availability);
}
