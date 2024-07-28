package com.dasun.library_manager_core.api.controller;

import com.dasun.library_manager_core.api.dto.BorrowerDetailsDto;
import com.dasun.library_manager_core.api.service.BorrowDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookBorrowController {
    private final BorrowDetailsService borrowDetailsService;


    @PostMapping("/{bookid}/borrow")
    public ResponseEntity<BorrowerDetailsDto> borrowBook(@PathVariable Integer bookid, @Valid @RequestBody BorrowerDetailsDto borrowerDetailsDto) {
        borrowerDetailsDto.setBookId(bookid);
        BorrowerDetailsDto temp = borrowDetailsService.borrowBook(bookid, borrowerDetailsDto);
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<BorrowerDetailsDto> returnBook(@PathVariable Integer id,
                                                      @Valid @RequestBody BorrowerDetailsDto borrowerDetailsDto) {
        borrowerDetailsDto.setBookId(id);
        return new ResponseEntity<>(borrowDetailsService.returnBook(id, borrowerDetailsDto), HttpStatus.ACCEPTED);
    }
}
