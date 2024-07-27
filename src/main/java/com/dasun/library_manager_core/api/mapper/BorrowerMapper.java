package com.dasun.library_manager_core.api.mapper;

import com.dasun.library_manager_core.api.dto.BorrowerDto;
import com.dasun.library_manager_core.api.entity.Borrower;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


public class BorrowerMapper {
    public static BorrowerDto borrowerToBorrowerDto(Borrower borrower){
        BorrowerDto borrowerDto = new BorrowerDto();
        borrowerDto.setName(borrower.getName());
        borrowerDto.setEmail(borrower.getEmail());
        borrowerDto.setId(borrower.getId());
        return borrowerDto;
    }

    public static Borrower borrowerDtoToBorrower(BorrowerDto borrowerDto){
        Borrower borrower = new Borrower();
        borrower.setName(borrowerDto.getName());
        borrower.setEmail(borrowerDto.getEmail());
        return borrower;
    }
}
