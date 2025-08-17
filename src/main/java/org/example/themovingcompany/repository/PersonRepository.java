// File: src/main/java/org/example/themovingcompany/repository/PersonRepository.java

package org.example.themovingcompany.repository;

import org.example.themovingcompany.model.Person;
import org.example.themovingcompany.model.enums.PersonRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional; // Make sure this import exists

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByPersonRole(PersonRole role);
    List<Person> findByPersonRoleAndArchived(PersonRole role, boolean archived);

    // ADD THIS LINE - This is the missing method
    Optional<Person> findByEmail(String email);
}