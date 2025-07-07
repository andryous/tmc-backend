package org.example.themovingcompany.service;

import org.example.themovingcompany.model.Person;
import org.example.themovingcompany.model.enums.PersonRole;
import org.example.themovingcompany.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
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

    // Returns all persons that match the given role (CUSTOMER or CONSULTANT)
    public List<Person> getPersonsByRole(String role) {
        PersonRole parsedRole = PersonRole.valueOf(role.toUpperCase()); // Convert string to enum
        return personRepository.findByPersonRole(parsedRole);
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

    // Updates an existing person with new data.
// Returns Optional.of(person) if found and updated, or Optional.empty() if not found.
    public Optional<Person> updatePerson(Long id, Person updatedPerson) {
        // First, check if the person with the given ID exists
        return personRepository.findById(id)
                .map(existingPerson -> {
                    // If found, update all fields with new values
                    existingPerson.setFirstName(updatedPerson.getFirstName());
                    existingPerson.setLastName(updatedPerson.getLastName());
                    existingPerson.setEmail(updatedPerson.getEmail());
                    existingPerson.setPhoneNumber(updatedPerson.getPhoneNumber());
                    existingPerson.setAddress(updatedPerson.getAddress());
                    existingPerson.setPersonRole(updatedPerson.getPersonRole());

                    // Save the updated person in the database
                    return personRepository.save(existingPerson);
                });

    }

    // PATCH / Partially updates fields of an existing person.
    public Person patchPerson(Long id, Map<String, Object> updates) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Person not found with id: " + id));

        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case "firstName":
                    if (value instanceof String && StringUtils.hasText((String) value)) {
                        person.setFirstName((String) value);
                    }
                    break;
                case "lastName":
                    if (value instanceof String && StringUtils.hasText((String) value)) {
                        person.setLastName((String) value);
                    }
                    break;
                case "email":
                    if (value instanceof String && StringUtils.hasText((String) value)) {
                        person.setEmail((String) value);
                    }
                    break;
                case "phoneNumber":
                    if (value instanceof String && StringUtils.hasText((String) value)) {
                        person.setPhoneNumber((String) value);
                    }
                    break;
                case "address":
                    if (value instanceof String && StringUtils.hasText((String) value)) {
                        person.setAddress((String) value);
                    }
                    break;
                case "personRole":
                    if (value instanceof String) {
                        person.setPersonRole(PersonRole.valueOf(((String) value).toUpperCase()));
                    }
                    break;
                default:
                    // Ignore unknown fields or handle as needed
                    break;
            }
        }

        return personRepository.save(person);
    }



    }

