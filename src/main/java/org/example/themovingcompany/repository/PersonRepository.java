package org.example.themovingcompany.repository;

import org.example.themovingcompany.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.themovingcompany.repository.PersonRepository;


public interface PersonRepository
        extends JpaRepository<Person, Long> {
// Spring will auto-generate all CRUD methods

}
