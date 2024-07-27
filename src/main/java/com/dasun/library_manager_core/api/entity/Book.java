package com.dasun.library_manager_core.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book extends PersistedObject{


    @NotNull
    @Column(name = "is_borrowed", nullable = false)
    private Boolean isBorrowed = false;

    @OneToMany(mappedBy = "book")
    private Set<BorrowDetails> borrowRecords = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_details_id")
    private BookDetails bookDetails;

    public Book(long id, Boolean isBorrowed, Set<BorrowDetails> borrowRecords, BookDetails bookDetails) {
        super(id);
        this.isBorrowed = isBorrowed;
        this.borrowRecords = borrowRecords;
        this.bookDetails = bookDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
