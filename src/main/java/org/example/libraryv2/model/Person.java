package org.example.libraryv2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
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

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Book> books;


    @Override
    public String toString() {
        return "Person{" +
                "id=" + personId +
                ", person_name='" + personName + '\'' +
                ", person_birth_years=" + personBirthYears +
                '}';
    }
}
