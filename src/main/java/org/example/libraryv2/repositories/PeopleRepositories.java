package org.example.libraryv2.repositories;

import org.example.libraryv2.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepositories extends JpaRepository<Person,Integer> {
    Page<Person> findAll(Pageable pageable);
}
