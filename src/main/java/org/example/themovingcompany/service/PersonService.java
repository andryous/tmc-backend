package org.example.themovingcompany.service;

import org.example.themovingcompany.model.Person;
import org.example.themovingcompany.model.enums.PersonRole;
import org.example.themovingcompany.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    // Constructor injection of the repository
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // Returns the full list of persons
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    // Returns persons filtered by role
    public List<Person> getPersonsByRole(String roleString) {
        try {
            PersonRole role = PersonRole.valueOf(roleString.toUpperCase());
            return personRepository.findByPersonRole(role);
        } catch (IllegalArgumentException e) {
            return Collections.emptyList(); // return empty list if role is invalid
        }
    }

    // Returns archived customers only (moved here from controller)
    public List<Person> getArchivedCustomers() {
        return personRepository.findByPersonRoleAndArchived(PersonRole.CUSTOMER, true);
    }

    // Returns a single person by ID
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    // Creates a new person
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    // Deletes a person by ID
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    // Fully replaces a person with new data
    public Optional<Person> updatePerson(Long id, Person updatedPerson) {
        return personRepository.findById(id).map(existing -> {
            existing.setFirstName(updatedPerson.getFirstName());
            existing.setLastName(updatedPerson.getLastName());
            existing.setEmail(updatedPerson.getEmail());
            existing.setPhoneNumber(updatedPerson.getPhoneNumber());
            existing.setAddress(updatedPerson.getAddress());
            existing.setPersonRole(updatedPerson.getPersonRole());
            existing.setArchived(updatedPerson.isArchived());
            return personRepository.save(existing);
        });
    }

    // Partially updates a person with specific fields
    public Person patchPerson(Long id, Map<String, Object> updates) {
        Person person = personRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Person not found with id " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "firstName" -> person.setFirstName((String) value);
                case "lastName" -> person.setLastName((String) value);
                case "email" -> person.setEmail((String) value);
                case "phoneNumber" -> person.setPhoneNumber((String) value);
                case "address" -> person.setAddress((String) value);
                case "archived" -> person.setArchived((Boolean) value);
                case "personRole" -> person.setPersonRole(PersonRole.valueOf((String) value));
            }
        });

        return personRepository.save(person);
    }
}
