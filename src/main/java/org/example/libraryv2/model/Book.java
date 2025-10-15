package org.example.libraryv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.example.libraryv2.services.BookService;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "book_author")
    private String bookAuthor;

    @Column(name = "book_years")
    private int bookYears;

    @Column(name = "issued_at")
    private LocalDate time;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "p_id",referencedColumnName = "person_id")
    private Person owner;

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + bookId +
                ", book_title='" + bookTitle + '\'' +
                ", book_author='" + bookAuthor + '\'' +
                ", book_years=" + bookYears +
                '}';
    }
}
