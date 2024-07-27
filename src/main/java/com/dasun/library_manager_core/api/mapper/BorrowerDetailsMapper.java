package com.dasun.library_manager_core.api.mapper;

import com.dasun.library_manager_core.api.dto.BorrowerDetailsDto;
import com.dasun.library_manager_core.api.dto.BorrowerDto;
import com.dasun.library_manager_core.api.entity.BorrowDetails;
import com.dasun.library_manager_core.api.entity.Borrower;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BorrowerDetailsMapper {
    BorrowerDetailsMapper INSTANCE = Mappers.getMapper(BorrowerDetailsMapper.class);

    @Mapping(source = "borrowDate", target = "borrowDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "borrower.id", target = "borrowerId")
    @Mapping(source = "returnDate", target = "returnDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    BorrowerDetailsDto toDto(BorrowDetails borrowDetails);
}
