package com.dasun.library_manager_core.api.dto;

import com.dasun.library_manager_core.api.entity.BookDetails;
import com.dasun.library_manager_core.api.entity.BorrowDetails;
import com.dasun.library_manager_core.api.entity.PersistedObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private long id;
    @NotBlank(message = "Book title is mandatory")
    private String title;
    @NotBlank(message = "Book Author name is mandatory")
    private String author;
    @NotBlank(message = "Book isbn name is mandatory")
    private String isbn;
}
