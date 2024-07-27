package com.dasun.library_manager_core.api.service.impl;

import com.dasun.library_manager_core.api.dto.BookDto;
import com.dasun.library_manager_core.api.dto.BorrowerDto;
import com.dasun.library_manager_core.api.entity.Book;
import com.dasun.library_manager_core.api.entity.BookDetails;
import com.dasun.library_manager_core.api.entity.Borrower;
import com.dasun.library_manager_core.api.mapper.BookDetailsMapper;
import com.dasun.library_manager_core.api.mapper.BookMapper;
import com.dasun.library_manager_core.api.mapper.BorrowerMapper;
import com.dasun.library_manager_core.api.repository.BookDetailsRepository;
import com.dasun.library_manager_core.api.repository.BookRepository;
import com.dasun.library_manager_core.api.repository.BorrowerRepository;
import com.dasun.library_manager_core.api.service.BookService;
import com.dasun.library_manager_core.api.service.BorrowerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookDetailsRepository bookDetailsRepository;


    @Override
    public BookDto addBook(BookDto bookDto) {
        // Log the start of the book addition process with the provided ISBN
        log.info("Attempting to add book with ISBN: {}", bookDto.getIsbn());

        // Check if a book with the same ISBN already exists in the repository
        Optional<BookDetails> existingBookDetails = bookDetailsRepository.findByIsbn(bookDto.getIsbn());

        if (existingBookDetails.isPresent()) {
            // If a book with the same ISBN is found, check if the author and title match
            BookDetails bookDetails = existingBookDetails.get();
            if (!(bookDetails.getAuthor().equals(bookDto.getAuthor()) && bookDetails.getTitle().equals(bookDto.getTitle()))) {
                // Log an error if the author or title differs for the same ISBN
                log.error("Book with an identical isbn {} with different author/title found", bookDto.getIsbn());
                // Throw an exception to prevent adding a book with a conflicting ISBN
                throw new IllegalArgumentException("Book with an identical ISBN with different author/title found");
            }
        } else {
            // If no existing book is found, create a new BookDetails entity
            log.info("No existing book found with ISBN {}. Creating a new book entry.", bookDto.getIsbn());
            BookDetails bookDetails = BookDetailsMapper.toBookDetails(bookDto);
            existingBookDetails = Optional.of(bookDetailsRepository.save(bookDetails));
        }

        // Create a new Book entity and associate it with the BookDetails
        Book book = new Book();
        book.setBookDetails(existingBookDetails.get());
        book = bookRepository.save(book);

        // Log the successful addition of the new book with its ID
        log.info("Book with ISBN {} has been added successfully with ID: {}", bookDto.getIsbn(), book.getId());

        // Convert the saved Book entity to a BookDto and return it
        return BookMapper.toBookDto(book);
    }

    @Override
    public Page<BookDto> getAllBooks(int page, int size) {
        // Create a Pageable object to define pagination
        Pageable pageable = PageRequest.of(page, size);

        log.debug("Fetching books with pagination: page={}, size={}", page, size);

        // Fetch a page of Book entities from the repository
        Page<Book> bookPage = bookRepository.findAll(pageable);

        // Convert Page<Book> to Page<BookDto> using BookMapper
        Page<BookDto> bookDtoPage = bookPage.map(BookMapper::toBookDto);

        log.info("Fetched {} books from page {} with size {}", bookDtoPage.getNumberOfElements(), page, size);

        return bookDtoPage;

    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        return null;
    }

    @Override
    public void deleteBook(Long bookId) {

    }

    @Override
    public List<BookDto> getBooksByAvailability(boolean availability) {
        List<Book> availableBooks = bookRepository.findByIsBorrowed(availability);
        return availableBooks.stream()
                .map(BookMapper::toBookDto)
                .toList();
    }

}
