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

// Partially updates a person by only changing the provided fields.
// Returns Optional.of(person) if found and updated, or Optional.empty() if not found.
        public Optional<Person> partialUpdatePerson(Long id, Person updates) {
            // Check if the person exists in the database
            return personRepository.findById(id)
                    .map(existingPerson -> {
                        // Update only the fields that are not null (they were sent by the client)

                        if (updates.getFirstName() != null) {
                            existingPerson.setFirstName(updates.getFirstName());
                        }
                        if (updates.getLastName() != null) {
                            existingPerson.setLastName(updates.getLastName());
                        }
                        if (updates.getEmail() != null) {
                            existingPerson.setEmail(updates.getEmail());
                        }
                        if (updates.getPhoneNumber() != null) {
                            existingPerson.setPhoneNumber(updates.getPhoneNumber());
                        }
                        if (updates.getAddress() != null) {
                            existingPerson.setAddress(updates.getAddress());
                        }
                        if (updates.getPersonRole() != null) {
                            existingPerson.setPersonRole(updates.getPersonRole());
                        }

                        // Save the partially updated person to the database
                        return personRepository.save(existingPerson);
                    });
        }



    }

