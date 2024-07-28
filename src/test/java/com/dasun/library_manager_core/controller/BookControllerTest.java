package com.dasun.library_manager_core.controller;

import com.dasun.library_manager_core.api.controller.BookBorrowController;
import com.dasun.library_manager_core.api.controller.BookController;
import com.dasun.library_manager_core.api.dto.BookDto;
import com.dasun.library_manager_core.api.dto.BorrowerDetailsDto;
import com.dasun.library_manager_core.api.entity.BookDetails;
import com.dasun.library_manager_core.api.service.BookService;
import com.dasun.library_manager_core.api.service.BorrowDetailsService;
import com.dasun.library_manager_core.config.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {BookController.class})
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void BookController_AddNewBook_Test() throws Exception {
        BookDto requestDto = new BookDto();
        requestDto.setId(1);
        requestDto.setAuthor("Tolkien, J.R.R.");
        requestDto.setTitle("The Hobbit");
        requestDto.setIsbn("9780140283334");

        when(bookService.addBook(requestDto)).thenReturn(requestDto);

        ResultActions response = mockMvc.perform(post("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is((int)requestDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", CoreMatchers.is(requestDto.getAuthor())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn", CoreMatchers.is(requestDto.getIsbn())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(requestDto.getTitle())));

    }
}
