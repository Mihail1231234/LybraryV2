package org.example.libraryv2.services;

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

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final PeopleService peopleService;

    int page=0;
    int size=5;

    public BookService(BookRepository bookRepository, PeopleService peopleService) {
        this.bookRepository = bookRepository;
        this.peopleService = peopleService;
    }

    public Page<Book> getBooksPage(int page,int size,String sort1){
        Sort sort;
        if (sort1 == null || sort1.isEmpty()) {
            sort = Sort.by("bookId").ascending();
        } else if (sort1.equals("desc")) {
            sort = Sort.by("bookYears").descending();
        } else {
            sort = Sort.by("bookYears").ascending();
        }
        return bookRepository.findAll(PageRequest.of(page,size,sort));
    }
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public List<Book> findBookByBookTitle(String title){
        if (title==null) return null;
        return bookRepository.findBooksByBookTitleIsStartingWithIgnoreCase(title);
    }

    public Book findOne(int id){
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book bookUpdate){
        Book existingBook = bookRepository.findById(id).orElseThrow();
        existingBook.setBookTitle(bookUpdate.getBookTitle());
        existingBook.setBookAuthor(bookUpdate.getBookAuthor());
        existingBook.setBookYears(bookUpdate.getBookYears());
        existingBook.setOwner(bookUpdate.getOwner());
        existingBook.setTime(bookUpdate.getTime());
    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }

    @Transactional
    public Person findPerson(int id){
        Optional<Book> book= bookRepository.findById(id);
        if (book.isPresent() && book.get().getOwner()!=null) return peopleService.findOne(book.get().getOwner().getId());
        return null;
    }
    @Transactional
    public Book assignOwnerToBook(Integer bookId,Integer peopleId){
        Person person=peopleService.findOne(peopleId);
        Book book= bookRepository.findById(bookId).orElse(null);

        book.setOwner(person);
        book.setTime(LocalDate.now());
        return bookRepository.save(book);
    }

    @Transactional
    public Book delPer(int id){
        Book book=bookRepository.findById(id).orElse(null);
        book.setOwner(null);
        book.setTime(null);
        return bookRepository.save(book);
    }

    public void test(){
        System.out.println("Testing debug");
    }

    public boolean isBookOverdue(Book book){
        if (book.getTime()==null)return  false;
        LocalDate now =LocalDate.now();
        return book.getTime().minusDays(15).isBefore(now);
    }
}
