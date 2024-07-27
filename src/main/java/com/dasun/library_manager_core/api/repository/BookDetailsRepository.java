package com.dasun.library_manager_core.api.repository;

import com.dasun.library_manager_core.api.entity.Book;
import com.dasun.library_manager_core.api.entity.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookDetailsRepository extends JpaRepository<BookDetails, Long> {
    Optional<BookDetails> findByIsbn(String isbn);
}
