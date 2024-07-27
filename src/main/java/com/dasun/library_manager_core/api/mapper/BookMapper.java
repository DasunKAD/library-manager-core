package com.dasun.library_manager_core.api.mapper;

import com.dasun.library_manager_core.api.dto.BookDto;
import com.dasun.library_manager_core.api.entity.Book;
import com.dasun.library_manager_core.api.entity.BookDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "bookDetails.title", target = "title")
    @Mapping(source = "bookDetails.author", target = "author")
    @Mapping(source = "bookDetails.isbn", target = "isbn")
    BookDto bookToBookDto(Book book);

    @Mapping(source = "title", target = "bookDetails.title")
    @Mapping(source = "author", target = "bookDetails.author")
    @Mapping(source = "isbn", target = "bookDetails.isbn")
    Book bookDtoToBook(BookDto bookDto);
}