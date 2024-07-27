package com.dasun.library_manager_core.service;

import com.dasun.library_manager_core.api.dto.BorrowerDetailsDto;
import com.dasun.library_manager_core.api.entity.Book;
import com.dasun.library_manager_core.api.entity.BookDetails;
import com.dasun.library_manager_core.api.entity.BorrowDetails;
import com.dasun.library_manager_core.api.entity.Borrower;
import com.dasun.library_manager_core.api.mapper.BorrowerDetailsMapper;
import com.dasun.library_manager_core.api.repository.BookDetailsRepository;
import com.dasun.library_manager_core.api.repository.BookRepository;
import com.dasun.library_manager_core.api.repository.BorrowDetailsRepository;
import com.dasun.library_manager_core.api.repository.BorrowerRepository;
import com.dasun.library_manager_core.api.service.BookService;
import com.dasun.library_manager_core.api.service.BorrowDetailsService;
import com.dasun.library_manager_core.util.CommonUtils;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BorrowDetailsServiceTest {
    @Mock
    private BorrowerDetailsMapper mapper;

    @Mock
    private EntityManager entityManager;

    @Mock
    private BorrowDetailsRepository borrowDetailsRepository;

    @Mock
    private BorrowerRepository borrowerRepository;

    @Mock
    private BookRepository bookRepository;


    @InjectMocks
    private BorrowDetailsService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testbookingWithUnavailableBook() {
        BorrowerDetailsDto dto = new BorrowerDetailsDto();
        dto.setBookId(1);
        dto.setBorrowerId(1);
        dto.setBorrowDate(CommonUtils.formatLocalDateTime(LocalDateTime.now()));

        Book book = new Book();
        book.setId(1);
        book.setIsBorrowed(Boolean.TRUE); // The book is not available

        Borrower borrower = new Borrower();
        borrower.setId(1);

        when(bookRepository.findById(dto.getBookId())).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(dto.getBorrowerId())).thenReturn(Optional.of(borrower));
        // mock the result of save method to return the passed input arg (to simulate to how jpa repo works)
        when(bookRepository.save(any(Book.class))).thenAnswer(i -> i.getArguments()[0]);
        // Expecting exception as the book is not available to borrow
        assertThrows(IllegalStateException.class, () -> service.borrowBook(1, dto));

        verify(bookRepository).findById(dto.getBorrowerId());
        verify(borrowerRepository).findById(dto.getBorrowerId());
        // expecting the save methods to  have  never been called as we cannot borrow the unavailable book
        verify(bookRepository, never()).save(any(Book.class));
        verify(borrowDetailsRepository, never()).save(any(BorrowDetails.class));

    }

//    @Test
//    void testBurrowBookWhenBookIsAvailable() {
//        BorrowerDetailsDto dto = new BorrowerDetailsDto();
//        dto.setBookId(1);
//        dto.setBorrowerId(1);
//        dto.setBorrowDate(CommonUtils.formatLocalDateTime(LocalDateTime.now()));
//
//        Book book = new Book();
//        book.setId(1);
//        book.setIsBorrowed(Boolean.TRUE); // The book is not available
//
//        Borrower borrower = new Borrower();
//        borrower.setId(1);
//
//        when(bookRepository.findById(dto.getBookId())).thenReturn(Optional.of(book));
//        when(borrowerRepository.findById(dto.getBorrowerId())).thenReturn(Optional.of(borrower));
//        when(bookRepository.save(any(Book.class))).thenAnswer(i -> i.getArguments()[0]);
//        when(mapper.dtoToEntity(dto)).thenReturn(new BorrowRecord());
//        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenAnswer(i -> {
//            BorrowRecord saved = (BorrowRecord) i.getArguments()[0];
//            saved.setId(1);
//            return saved;
//        });
//
//        BorrowRecordDTO actualDto = service.borrowBook(1, dto);
//
//        assertEquals(dto.getBorrowerId(), actualDto.getBorrowerId());
//        assertEquals(dto.getBookId(), actualDto.getBookId());
//        assertNotNull(actualDto.getBorrowDate());
//        verify(entityManager).find(Book.class, dto.getBookId(), LockModeType.PESSIMISTIC_WRITE);
//        verify(borrowerRepository).findById(dto.getBorrowerId());
//        verify(borrowRecordRepository).save(any(BorrowRecord.class));
//    }

    @Test
    void testReturnBook() {
        long bookId = 1;
        long borrowerId = 1;

        Book book = new Book();
        book.setId(bookId);
        book.setIsBorrowed(Boolean.FALSE); // The book is currently borrowed

        Borrower borrower = new Borrower();
        borrower.setId(borrowerId);

        BorrowDetails borrowRecord = new BorrowDetails();
        borrowRecord.setBook(book);
        borrowRecord.setBorrower(borrower);
        borrowRecord.setBorrowDate(LocalDateTime.now());

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
        when(borrowDetailsRepository.findByBookAndBorrowerAndReturnDateIsNull(book, borrower)).thenReturn(Optional.of(borrowRecord));
        when(borrowDetailsRepository.save(any(BorrowDetails.class))).thenAnswer(i -> i.getArguments()[0]);

        BorrowerDetailsDto dto = new BorrowerDetailsDto();
        dto.setBookId(1);
        dto.setBorrowerId(1);
        dto.setBorrowDate(CommonUtils.formatLocalDateTime(LocalDateTime.now().minusWeeks(1)));

        BorrowerDetailsDto actualDto = service.returnBook(1, dto);
        assertEquals(bookId, actualDto.getBookId());
        assertEquals(borrowerId, actualDto.getBorrowerId());
        assertNotNull(actualDto.getReturnDate());
        assertFalse(book.getIsBorrowed());
        verify(bookRepository).findById(bookId);
        verify(borrowerRepository).findById(borrowerId);
        verify(borrowDetailsRepository).findByBookAndBorrowerAndReturnDateIsNull(book, borrower);
        verify(borrowDetailsRepository).save(any(BorrowDetails.class));
    }
}
