package com.dasun.library_manager_core.api.repository;

import com.dasun.library_manager_core.api.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
}
