package com.dasun.library_manager_core.api.mapper;

import com.dasun.library_manager_core.api.dto.BookDto;
import com.dasun.library_manager_core.api.entity.BookDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public class BookDetailsMapper {
    public static BookDetails toBookDetails(BookDto bookDto){
        BookDetails bookDetails = new BookDetails();
        bookDetails.setTitle(bookDto.getTitle());
        bookDetails.setIsbn(bookDto.getIsbn());
        bookDetails.setAuthor(bookDto.getAuthor());
        return bookDetails;
    }

    public static BookDto toBookDto(BookDetails bookDetails) {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(bookDetails.getTitle());
        bookDto.setIsbn(bookDetails.getIsbn());
        bookDto.setAuthor(bookDetails.getAuthor());
        return bookDto;
    }
}
