package org.example.themovingcompany.repository;

import org.example.themovingcompany.model.Person;
import org.example.themovingcompany.model.enums.PersonRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    // Spring will auto-generate all CRUD methods

    // Find all persons with a specific role (CUSTOMER or CONSULTANT)
    List<Person> findByPersonRole(PersonRole role);
}
