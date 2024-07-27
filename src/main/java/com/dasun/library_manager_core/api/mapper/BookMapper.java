package com.dasun.library_manager_core.api.mapper;

import com.dasun.library_manager_core.api.dto.BookDto;
import com.dasun.library_manager_core.api.entity.Book;
import com.dasun.library_manager_core.api.entity.BookDetails;
import org.mapstruct.Mapper;

@Mapper
public class BookMapper {

    public static BookDto toBookDto(Book book) {

        BookDto bookDto = new BookDto();
        bookDto.setTitle(book.getBookDetails().getTitle());
        bookDto.setIsbn(book.getBookDetails().getIsbn());
        bookDto.setAuthor(book.getBookDetails().getAuthor());
        bookDto.setId(book.getId());
        return bookDto;
    }
}
