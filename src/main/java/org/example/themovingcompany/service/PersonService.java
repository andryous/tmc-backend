package org.example.themovingcompany.service;

import org.example.themovingcompany.model.Person;
import org.example.themovingcompany.model.PersonRole;
import org.example.themovingcompany.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marks this class as a service component (used for business logic).
public class PersonService {

    private final PersonRepository personRepository;

    // Constructor injection: Spring injects the repository here automatically
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // Return all persons from the database.
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    //Returns a specific person by ID.
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    // This method saves a new person or updates an existing one
    public Person createPerson(Person person) { // Method name: createPerson. It receives a Person and returns a Person.

        // Save the person to the database using the repository.
        // personRepository is the tool (variable) that talks to the database.
        // .save() is a built-in method from JpaRepository.
        return personRepository.save(person);
    }

    //Delete a person by ID.
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
// More methods like update, findByEmail, etc. can be added later

}
