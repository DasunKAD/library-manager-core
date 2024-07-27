package com.dasun.library_manager_core.api.mapper;

import com.dasun.library_manager_core.api.dto.BorrowerDto;
import com.dasun.library_manager_core.api.entity.Borrower;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BorrowerMapper {
    BorrowerMapper INSTANCE = Mappers.getMapper(BorrowerMapper.class);

    BorrowerDto borrowerToBorrowerDto(Borrower borrower);

    Borrower borrowerDtoToBorrower(BorrowerDto borrowerDto);
}
