package org.example.libraryv2.services;

import org.example.libraryv2.model.Book;
import org.example.libraryv2.model.Person;
import org.example.libraryv2.repositories.BookRepository;
import org.example.libraryv2.repositories.PeopleRepositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PeopleService {

    private final PeopleRepositories peopleRepositories;
    private final BookRepository bookRepository;

    public PeopleService(PeopleRepositories peopleRepositories, BookRepository bookRepository) {
        this.peopleRepositories = peopleRepositories;
        this.bookRepository = bookRepository;
    }

    public List<Person> findAll(){
        return peopleRepositories.findAll();
    }

    public Page<Person> getPeople(int page,int size){
        return peopleRepositories.findAll(PageRequest.of(page,size, Sort.by("personId").ascending()));
    }

    public Person findOne(int id){return peopleRepositories.findById(id).orElse(null);}

    @Transactional
    public void save(Person person){peopleRepositories.save(person);}

    @Transactional
    public void updatePerson(int id,Person updatePerson){
        updatePerson.setPersonId(id);
        peopleRepositories.save(updatePerson);
    }

    @Transactional
    public void delete(int id){peopleRepositories.deleteById(id);}

    @Transactional
    public List<Book> getBook(Integer personId){
        return bookRepository.findByOwner_PersonId(personId)
                .stream()
                .filter(book -> book.getOwner().getPersonId()!=personId)
                .toList();
    }
}
