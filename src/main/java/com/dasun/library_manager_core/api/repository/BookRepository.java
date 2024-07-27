package com.dasun.library_manager_core.api.repository;

import com.dasun.library_manager_core.api.entity.Book;
import com.dasun.library_manager_core.api.entity.Borrower;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Book> findById(Long bookId);
    List<Book> findByIsBorrowed(Boolean isBorrowed);
}
