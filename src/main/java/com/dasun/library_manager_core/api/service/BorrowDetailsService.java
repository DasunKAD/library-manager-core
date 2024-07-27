package com.dasun.library_manager_core.api.service;

import com.dasun.library_manager_core.api.dto.BorrowerDetailsDto;

public interface BorrowDetailsService {
    public BorrowerDetailsDto borrowBook(long bookId, BorrowerDetailsDto borrowerDetailsDto);
    public BorrowerDetailsDto returnBook(long bookId, BorrowerDetailsDto borrowerDetailsDto);
}
