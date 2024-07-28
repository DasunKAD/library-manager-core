package com.dasun.library_manager_core.controller;

import com.dasun.library_manager_core.api.controller.BookBorrowController;
import com.dasun.library_manager_core.api.dto.BorrowerDetailsDto;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(BookBorrowController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {BookBorrowController.class})
class BookBorrowControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowDetailsService borrowDetailsService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void BookBorrowController_BorrowBook_Test() throws Exception {
        BorrowerDetailsDto requestDto = new BorrowerDetailsDto();
        requestDto.setBookId(1);
        requestDto.setBorrowerId(1);
        requestDto.setBorrowDate("2024-07-28");

        when(borrowDetailsService.borrowBook(requestDto.getBookId(),requestDto)).thenReturn(requestDto);

        ResultActions response = mockMvc.perform(post("/api/v1/book/1/borrow")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookId", CoreMatchers.is((int)requestDto.getBookId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.borrowerId", CoreMatchers.is((int)requestDto.getBorrowerId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.borrowDate", CoreMatchers.is(requestDto.getBorrowDate())));

    }
    @Test
     void BookBorrowController_ReturnBook_Test() throws Exception {
        BorrowerDetailsDto requestDto = new BorrowerDetailsDto();
        requestDto.setBookId(1);
        requestDto.setBorrowerId(1);
        requestDto.setReturnDate("2024-07-30");

        when(borrowDetailsService.returnBook(requestDto.getBookId(),requestDto)).thenReturn(requestDto);

        ResultActions response = mockMvc.perform(post("/api/v1/book/1/return")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        response.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookId", CoreMatchers.is((int)requestDto.getBookId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.borrowerId", CoreMatchers.is((int)requestDto.getBorrowerId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnDate", CoreMatchers.is(requestDto.getReturnDate())));
    }
}
