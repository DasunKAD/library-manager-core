package com.dasun.library_manager_core.api.mapper;


import com.dasun.library_manager_core.api.dto.BookDto;
import com.dasun.library_manager_core.api.entity.BookDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookDetailsMapper {
    BookDetailsMapper INSTANCE = Mappers.getMapper(BookDetailsMapper.class);

    BookDetails toBookDetails(BookDto bookDto);

    BookDto toBookDto(BookDetails bookDetails);
}
