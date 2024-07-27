package com.dasun.library_manager_core.api.mapper;

import com.dasun.library_manager_core.api.dto.BorrowerDetailsDto;
import com.dasun.library_manager_core.api.dto.BorrowerDto;
import com.dasun.library_manager_core.api.entity.BorrowDetails;
import com.dasun.library_manager_core.api.entity.Borrower;


public class BorrowerDetailsMapper {
    public static BorrowerDetailsDto toDto(BorrowDetails borrowDetails){
        BorrowerDetailsDto borrowerDetailsDto = new BorrowerDetailsDto();
        borrowerDetailsDto.setBorrowDate(borrowDetails.getBorrowDate().toString());
        borrowerDetailsDto.setBookId(borrowDetails.getBook().getId());
        borrowerDetailsDto.setBorrowerId(borrowDetails.getBorrower().getId());
        borrowerDetailsDto.setReturnDate(borrowDetails.getReturnDate().toString());
        return borrowerDetailsDto;
    }

    public static BorrowDetails toEntity(BorrowerDetailsDto borrowerDetailsDto){
        BorrowDetails borrowDetails = new BorrowDetails();
//        borrowDetails.setBorrowDate(borrowDetails.getBorrowDate().toString());
//        borrowDetails.setBookId(borrowDetails.getBook().getId());
//        borrowDetails.setBorrowerId(borrowDetails.getBorrower().getId());
//        borrowDetails.setReturnDate(borrowDetails.getReturnDate().toString());

        return borrowDetails;

    }
}
