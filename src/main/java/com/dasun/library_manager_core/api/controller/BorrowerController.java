package com.dasun.library_manager_core.api.controller;

import com.dasun.library_manager_core.api.dto.BorrowerDto;
import com.dasun.library_manager_core.api.entity.Borrower;
import com.dasun.library_manager_core.api.service.BorrowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/borrower")
@RequiredArgsConstructor
public class BorrowerController {
    private final BorrowerService borrowerService;

    @PostMapping("")
    public ResponseEntity<BorrowerDto> registerNewBorrower(@RequestBody BorrowerDto borrowerDto) {
        return new ResponseEntity<>(borrowerService.addBorrower(borrowerDto), HttpStatus.CREATED);
    }
}
