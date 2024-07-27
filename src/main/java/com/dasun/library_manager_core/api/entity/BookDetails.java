package com.dasun.library_manager_core.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "book_details")
public class BookDetails extends PersistedObject{
    @Size(max = 25)
    @NotNull
    @Column(name = "ISBN", nullable = false, length = 25)
    private String isbn;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 255)
    @NotNull
    @Column(name = "author", nullable = false)
    private String author;

    @OneToMany(mappedBy = "bookDetails")
    private Set<Book> bookCopies = new LinkedHashSet<>();


    public BookDetails(long id, String isbn, String title, String author, Set<Book> bookCopies) {
        super(id);
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.bookCopies = bookCopies;
    }

    public void addCopies(Book book) {
        bookCopies.add(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDetails bookInfo = (BookDetails) o;
        return Objects.equals(isbn, bookInfo.isbn) &&
                Objects.equals(title, bookInfo.title) &&
                Objects.equals(author, bookInfo.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, author);
    }
}
