package com.dasun.library_manager_core.controller;

import com.dasun.library_manager_core.api.controller.BookController;
import com.dasun.library_manager_core.api.controller.BorrowerController;
import com.dasun.library_manager_core.api.dto.BookDto;
import com.dasun.library_manager_core.api.dto.BorrowerDto;
import com.dasun.library_manager_core.api.service.BookService;
import com.dasun.library_manager_core.api.service.BorrowerService;
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

@WebMvcTest(BorrowerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {BorrowerController.class})
class BorrowerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowerService borrowerService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void BorrowerController_AddNewBorrower_Test() throws Exception {
        BorrowerDto requestDto = new BorrowerDto();
        requestDto.setId(1);
        requestDto.setName("Johne Peter");
        requestDto.setEmail("john.perez@gmail.com");

        when(borrowerService.addBorrower(requestDto)).thenReturn(requestDto);

        ResultActions response = mockMvc.perform(post("/api/v1/borrower")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is((int)requestDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(requestDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(requestDto.getEmail())));

    }
}
