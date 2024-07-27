package com.dasun.library_manager_core.api.service.impl;

import com.dasun.library_manager_core.api.dto.BorrowerDetailsDto;
import com.dasun.library_manager_core.api.entity.Book;
import com.dasun.library_manager_core.api.entity.BorrowDetails;
import com.dasun.library_manager_core.api.entity.Borrower;
import com.dasun.library_manager_core.api.mapper.BookDetailsMapper;
import com.dasun.library_manager_core.api.mapper.BorrowerDetailsMapper;
import com.dasun.library_manager_core.api.repository.BookDetailsRepository;
import com.dasun.library_manager_core.api.repository.BookRepository;
import com.dasun.library_manager_core.api.repository.BorrowDetailsRepository;
import com.dasun.library_manager_core.api.repository.BorrowerRepository;
import com.dasun.library_manager_core.api.service.BorrowDetailsService;
import com.dasun.library_manager_core.api.exception.DataNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Log4j2
public class BorrowDetailsServiceImpl implements BorrowDetailsService {
    private final BorrowerRepository borrowerRepository;
    private final BookRepository bookRepository;
    private final BorrowDetailsRepository borrowDetailsRepository;

    @Override
    @Transactional
    public BorrowerDetailsDto borrowBook(long bookId, BorrowerDetailsDto borrowerDetailsDto) {
        log.info("Attempting to borrow book with ID: {} for borrower: {}", bookId, borrowerDetailsDto);
        // Retrieve the borrower details from the provided DTO
        Borrower borrower = getBorrower(borrowerDetailsDto);
        log.debug("Retrieved borrower details: {}", borrower);

        // Retrieve the book details using the provided bookId
        Book book = getBook(bookId);
        log.debug("Retrieved book details: {}", book);

        if (Boolean.TRUE.equals(book.getIsBorrowed())) {
            log.error("Book with ID: {} is currently unavailable for borrowing", bookId);
            throw new IllegalStateException("Book is currently unavailable for borrowing");
        }

        // Create and populate the BorrowDetails object
        BorrowDetails borrowDetails = new BorrowDetails();
        borrowDetails.setBook(book);
        borrowDetails.setBorrower(borrower);
        borrowDetails.setBorrowDate(LocalDateTime.now());
        borrowDetails.setReturnDate(null);

        log.debug("Created new borrow details: {}", borrowDetails);

        // Update the book's status to borrowed
        book.setIsBorrowed(Boolean.TRUE);

        // Save the updated book and borrow details to the repository
        bookRepository.save(book);
        log.info("Saved updated book details to repository");
        BorrowDetails savedBorrowDetails = borrowDetailsRepository.save(borrowDetails);
        log.info("Saved new borrow details to repository");

        // Convert the saved BorrowRecord entity to a DTO and return it

        BorrowerDetailsDto borrowerDetailsDtoResult = BorrowerDetailsMapper.INSTANCE.toDto(savedBorrowDetails);
        log.info("Converted saved borrow details to DTO: {}", borrowerDetailsDtoResult);

        return borrowerDetailsDtoResult;
    }

    @Override
    @Transactional
    public BorrowerDetailsDto returnBook(long bookId, BorrowerDetailsDto borrowerDetailsDto) {
        log.info("Attempting to return book with ID: {} for borrower: {}", bookId, borrowerDetailsDto);

        // Retrieve the borrower details from the provided DTO
        Borrower borrower = getBorrower(borrowerDetailsDto);
        log.debug("Retrieved borrower details: {}", borrower);

        // Retrieve the book details using the provided bookId
        Book book = getBook(bookId);
        log.debug("Retrieved book details: {}", book);

        // Find the existing borrow details entry for the book and borrower that has not yet been returned
        BorrowDetails borrowDetails = borrowDetailsRepository.findByBookAndBorrowerAndReturnDateIsNull(book, borrower)
                .orElseThrow(() -> new IllegalStateException("The book is not currently borrowed by the borrower"));

        log.debug("Found borrow details for book ID: {} and borrower: {}", bookId, borrower);

        // Set the return date to the current time as the book is being returned
        borrowDetails.setReturnDate(LocalDateTime.now());
        log.info("Set return date for borrow details to: {}", borrowDetails.getReturnDate());

        // Update the isBorrowed status of the book to false as the book is being returned
        // status = true indicates book is available, status = false indicates book is unavailable
        book.setIsBorrowed(Boolean.FALSE);
        log.info("Updated book status to available (ID: {})", bookId);

        // Save the updated book and borrow details to the repository
        bookRepository.save(book);
        log.info("Saved updated book details to repository");

        BorrowDetails savedBorrowDetails = borrowDetailsRepository.save(borrowDetails);
        log.info("Saved updated borrow details to repository");

        // Convert the updated BorrowDetails entity to a DTO and return it
        BorrowerDetailsDto borrowerDetailsDtoResult = BorrowerDetailsMapper.INSTANCE.toDto(savedBorrowDetails);
        log.info("Converted updated borrow details to DTO: {}", borrowerDetailsDtoResult);

        return borrowerDetailsDtoResult;
    }


    private Book getBook(long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new DataNotFoundException("Book", "", ""));
    }

    private Borrower getBorrower(BorrowerDetailsDto borrowerDetailsDto) {
        return borrowerRepository.findById(borrowerDetailsDto.getBorrowerId())
                .orElseThrow(() -> new DataNotFoundException("Borrower", "", ""));
    }
}
