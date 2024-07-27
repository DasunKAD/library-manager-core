package com.dasun.library_manager_core.service;

import com.dasun.library_manager_core.api.dto.BookDto;
import com.dasun.library_manager_core.api.entity.Book;
import com.dasun.library_manager_core.api.entity.BookDetails;
import com.dasun.library_manager_core.api.repository.BookDetailsRepository;
import com.dasun.library_manager_core.api.repository.BookRepository;
import com.dasun.library_manager_core.api.service.BookService;
import com.dasun.library_manager_core.api.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookDetailsRepository bookDetailsRepository;

    private static List<Book> setUpNonBorrowedBooks() {
        List<Book> registeredBooks = new ArrayList<>();
        BookDetails bookDetails_1 = new BookDetails(1, "9780140283334", "The Hobbit", "Tolkien, J.R.R.", new HashSet<>());
        BookDetails bookDetails_2 = new BookDetails(2, "9780316769488", "The Catcher in the Rye", "Salinger, J.D.", new HashSet<>());
        BookDetails bookDetails_3 = new BookDetails(3, "9780061120084", "To Kill a Mockingbird", "Lee, Harper", new HashSet<>());
        BookDetails bookDetails_4 = new BookDetails(4, "9780439064873", "Harry Potter and the Chamber of Secrets", "Rowling, J.K.", new HashSet<>());


        //2 copies of bookDetails (ISBN 9780140283334(
        Book book1 = new Book(1, false, new HashSet<>(), bookDetails_1);
        Book book2 = new Book(2, false, new HashSet<>(), bookDetails_1);
        //1 copy of bookDetails (ISBN 9780316769488)
        Book book3 = new Book(3, false, new HashSet<>(), bookDetails_2);
        //2 copies of bookDetails (ISBN 9780061120084)
        Book book4 = new Book(4, false, new HashSet<>(), bookDetails_3);
        Book book5 = new Book(5, false, new HashSet<>(), bookDetails_3);
        //1 copy of bookDetails(ISBN 9780439064873)
        Book book6 = new Book(6, false, new HashSet<>(), bookDetails_4);

        // add books back to bookInfo list (many to relationship between book and bookInfo)
        bookDetails_1.addCopies(book1);
        bookDetails_1.addCopies(book2);
        bookDetails_2.addCopies(book3);
        bookDetails_3.addCopies(book4);
        bookDetails_3.addCopies(book5);
        bookDetails_4.addCopies(book6);

        // set up registered book result
        registeredBooks.add(book1);
        registeredBooks.add(book2);
        registeredBooks.add(book3);
        registeredBooks.add(book4);
        registeredBooks.add(book5);
        registeredBooks.add(book6);
        return registeredBooks;
    }

    private static List<Book> setUpBorrowedBooks() {
        List<Book> registeredBooks = new ArrayList<>();
        BookDetails bookDetails_1 = new BookDetails(1, "9780140283334", "The Hobbit", "Tolkien, J.R.R.", new HashSet<>());
        BookDetails bookDetails_2 = new BookDetails(2, "9780316769488", "The Catcher in the Rye", "Salinger, J.D.", new HashSet<>());
        BookDetails bookDetails_3 = new BookDetails(3, "9780061120084", "To Kill a Mockingbird", "Lee, Harper", new HashSet<>());
        BookDetails bookDetails_4 = new BookDetails(4, "9780439064873", "Harry Potter and the Chamber of Secrets", "Rowling, J.K.", new HashSet<>());


        //2 copies of bookDetails (ISBN 9780140283334(
        Book book1 = new Book(1, false, new HashSet<>(), bookDetails_1);
        Book book2 = new Book(2, false, new HashSet<>(), bookDetails_1);
        //1 copy of bookDetails (ISBN 9780316769488)
        Book book3 = new Book(3, true, new HashSet<>(), bookDetails_2);
        //2 copies of bookDetails (ISBN 9780061120084)
        Book book4 = new Book(4, false, new HashSet<>(), bookDetails_3);
        Book book5 = new Book(5, true, new HashSet<>(), bookDetails_3);
        //1 copy of bookDetails(ISBN 9780439064873)
        Book book6 = new Book(6, true, new HashSet<>(), bookDetails_4);

        // add books back to bookInfo list (many to relationship between book and bookInfo)
        bookDetails_1.addCopies(book1);
        bookDetails_1.addCopies(book2);
        bookDetails_2.addCopies(book3);
        bookDetails_3.addCopies(book4);
        bookDetails_3.addCopies(book5);
        bookDetails_4.addCopies(book6);

        // set up registered book result
        registeredBooks.add(book1);
        registeredBooks.add(book2);
        registeredBooks.add(book3);
        registeredBooks.add(book4);
        registeredBooks.add(book5);
        registeredBooks.add(book6);
        return registeredBooks;
    }

    private static List<Book> setUpAllBorrowedBooks() {
        List<Book> registeredBooks = new ArrayList<>();
        BookDetails bookDetails_1 = new BookDetails(1, "9780140283334", "The Hobbit", "Tolkien, J.R.R.", new HashSet<>());
        BookDetails bookDetails_2 = new BookDetails(2, "9780316769488", "The Catcher in the Rye", "Salinger, J.D.", new HashSet<>());
        BookDetails bookDetails_3 = new BookDetails(3, "9780061120084", "To Kill a Mockingbird", "Lee, Harper", new HashSet<>());
        BookDetails bookDetails_4 = new BookDetails(4, "9780439064873", "Harry Potter and the Chamber of Secrets", "Rowling, J.K.", new HashSet<>());


        //2 copies of bookDetails (ISBN 9780140283334(
        Book book1 = new Book(1, true, new HashSet<>(), bookDetails_1);
        Book book2 = new Book(2, true, new HashSet<>(), bookDetails_1);
        //1 copy of bookDetails (ISBN 9780316769488)
        Book book3 = new Book(3, true, new HashSet<>(), bookDetails_2);
        //2 copies of bookDetails (ISBN 9780061120084)
        Book book4 = new Book(4, true, new HashSet<>(), bookDetails_3);
        Book book5 = new Book(5, true, new HashSet<>(), bookDetails_3);
        //1 copy of bookDetails(ISBN 9780439064873)
        Book book6 = new Book(6, true, new HashSet<>(), bookDetails_4);

        // add books back to bookInfo list (many to relationship between book and bookInfo)
        bookDetails_1.addCopies(book1);
        bookDetails_1.addCopies(book2);
        bookDetails_2.addCopies(book3);
        bookDetails_3.addCopies(book4);
        bookDetails_3.addCopies(book5);
        bookDetails_4.addCopies(book6);

        // set up registered book result
        registeredBooks.add(book1);
        registeredBooks.add(book2);
        registeredBooks.add(book3);
        registeredBooks.add(book4);
        registeredBooks.add(book5);
        registeredBooks.add(book6);
        return registeredBooks;
    }

    private static List<Book> setUpBooksForTest_Empty() {
        return new ArrayList<>();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBooksAvailableForBorrow_whenNoneBorrowed() {

        List<Book> registeredBooks = setUpNonBorrowedBooks();

        when(bookRepository.findByIsBorrowed(Boolean.FALSE)).thenReturn(registeredBooks);
        List<BookDto> nonBorrowedBooks = bookService.getBooksByAvailability(Boolean.FALSE);
        assertEquals(6, nonBorrowedBooks.size());
    }

    @Test
    void testGetBooksAvailableForBorrow_whenSomeBorrowed() {

        List<Book> registeredBooks = setUpBorrowedBooks();

        when(bookRepository.findByIsBorrowed(Boolean.FALSE)).thenReturn(registeredBooks);

        List<BookDto> result = bookService.getBooksByAvailability(Boolean.FALSE);

        assertEquals(3, result.size());
    }

    @Test
    void testGetBooksAvailableForBorrow_whenAllBorrowed() {

        List<Book> registeredBooks = setUpBooksForTest_Empty();

        when(bookRepository.findByIsBorrowed(Boolean.FALSE)).thenReturn(registeredBooks);

        List<BookDto> result = bookService.getBooksByAvailability(Boolean.FALSE);

        assertEquals(0, result.size());
    }

}
