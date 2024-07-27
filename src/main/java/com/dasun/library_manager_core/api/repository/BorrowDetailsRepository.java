package com.dasun.library_manager_core.api.repository;

import com.dasun.library_manager_core.api.entity.Book;
import com.dasun.library_manager_core.api.entity.BorrowDetails;
import com.dasun.library_manager_core.api.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowDetailsRepository extends JpaRepository<BorrowDetails, Long> {
    Optional<BorrowDetails> findByBookAndBorrowerAndReturnDateIsNull(Book book, Borrower borrower);
}
