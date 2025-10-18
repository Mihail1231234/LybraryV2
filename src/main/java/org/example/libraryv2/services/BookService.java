package org.example.libraryv2.services;

import lombok.RequiredArgsConstructor;
import org.example.libraryv2.model.Book;
import org.example.libraryv2.model.Person;
import org.example.libraryv2.repositories.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final PeopleService peopleService;


    public Page<Book> getBooksPage(int page, int size, String sort1) {
        Sort sort;
        if (sort1 == null || sort1.isEmpty()) {
            sort = Sort.by("bookId").ascending();
        } else if (sort1.equals("desc")) {
            sort = Sort.by("bookYears").descending();
        } else {
            sort = Sort.by("bookYears").ascending();
        }
        return bookRepository.findAll(PageRequest.of(page, size, sort));
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findBookByBookTitle(String title) {

        /*if (title == null) {
            throw new RuntimeException("Строка не может быть пустой " + title);
        }*/

        if (title == null) {
            throw new RuntimeException("Строка не может быть пустой " + title);
        }
        return bookRepository.findBooksByBookTitleIsStartingWithIgnoreCase(title);
    }

    public Book findOne(int id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Не найден id " + id));
    }

    @Transactional
    public void save(Book book) {
        try {
            if (book != null) {
                bookRepository.save(book);
            }
        } catch (RuntimeException e) {
            e.getStackTrace();
        }

    }

    @Transactional
    public void update(int id, Book bookUpdate) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Не найден id " + id));
        existingBook.setBookTitle(bookUpdate.getBookTitle());
        existingBook.setBookAuthor(bookUpdate.getBookAuthor());
        existingBook.setBookYears(bookUpdate.getBookYears());
        existingBook.setOwner(bookUpdate.getOwner());
        existingBook.setTime(bookUpdate.getTime());
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public Person findPerson(int id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent() && book.get().getOwner() != null)
            return peopleService.findOne(book.get().getOwner().getPersonId());
        return null;
    }


    public Book assignOwnerToBook(Integer bookId, Integer peopleId) {
        Person person = peopleService.findOne(peopleId);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("книга по id не найдена " + bookId));

        book.setOwner(person);
        book.setTime(LocalDate.now());
        return bookRepository.save(book);
    }

    @Transactional
    public Book delPer(int id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Не найдена книга по id " + id));
        book.setOwner(null);
        book.setTime(null);
        return bookRepository.save(book);
    }

    public boolean isBookOverdue(Book book) {
        if (book.getTime() == null) return false;
        LocalDate now = LocalDate.now();
        return book.getTime().minusDays(15).isBefore(now);
    }
}
