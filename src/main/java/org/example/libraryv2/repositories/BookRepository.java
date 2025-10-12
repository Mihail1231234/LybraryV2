package org.example.libraryv2.repositories;

import org.example.libraryv2.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findByOwner_Id(Integer personId);
    Page<Book> findAll(Pageable pageable);
    List<Book> findBooksByBookTitleIsStartingWithIgnoreCase(String title);

}
