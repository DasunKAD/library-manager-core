package com.dasun.library_manager_core.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BorrowerDetailsDto {
    @NotNull(message = "borrowed bookId is mandatory")
    private long bookId;
    @NotNull(message = "borrower id is mandatory")
    private long borrowerId;
    private String borrowDate;
    private String returnDate;


    @Override
    public String toString() {
        return "BorrowRecordDTO{" +
                "bookId=" + bookId +
                ", borrowerId=" + borrowerId +
                '}';
    }
}
