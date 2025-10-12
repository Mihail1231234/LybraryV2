package org.example.libraryv2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.List;

@Entity
@Table(name="person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int personId;

    @Column(name = "person_name")
    private String personName;

    @Column(name = "person_birth_years")
    private String personBirthYears;

    @Email
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(String person_name, String personBirthYears, String email) {
        this.personName = person_name;
        this.personBirthYears = personBirthYears;
        this.email = email;
    }

    public Person() {
    }

    public int getId() {
        return personId;
    }

    public void setId(int id) {
        this.personId = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String person_name) {
        this.personName = person_name;
    }

    public String getPersonBirthYears() {
        return personBirthYears;
    }

    public void setPersonBirthYears(String person_birth_years) {
        this.personBirthYears = person_birth_years;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + personId +
                ", person_name='" + personName + '\'' +
                ", person_birth_years=" + personBirthYears +
                '}';
    }
}
