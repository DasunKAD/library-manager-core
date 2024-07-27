package com.dasun.library_manager_core.api.service;

import com.dasun.library_manager_core.api.dto.BorrowerDto;

import java.util.List;

public interface BorrowerService {
    public BorrowerDto addBorrower(BorrowerDto borrower);
    public BorrowerDto updateBorrower(BorrowerDto borrower);
    public void deleteBorrower(Long borrowerId);
    public BorrowerDto findBorrower(Long borrowerId);
    public List<BorrowerDto> findAllBorrowers();
}
