package org.example.libraryv2.model;

import jakarta.persistence.*;
import org.example.libraryv2.services.BookService;

import java.time.LocalDate;

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

    @ManyToOne
    @JoinColumn(name = "p_id",referencedColumnName = "person_id")
    private Person owner;

    public Book() {
    }

    public Book(int bookId, String book_title, String bookAuthor, int bookYears, LocalDate time, Person owner) {
        this.bookId = bookId;
        this.bookTitle = book_title;
        this.bookAuthor = bookAuthor;
        this.bookYears = bookYears;
        this.time = time;
        this.owner = owner;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int book_id) {
        this.bookId = book_id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String book_title) {
        this.bookTitle = book_title;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String book_author) {
        this.bookAuthor = book_author;
    }

    public int getBookYears() {
        return bookYears;
    }

    public void setBookYears(int book_years) {
        this.bookYears = book_years;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time =time;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }



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
