// File: src/main/java/org/example/themovingcompany/service/PersonService.java
package org.example.themovingcompany.service;

import org.example.themovingcompany.model.Person;
import org.example.themovingcompany.model.enums.PersonRole;
import org.example.themovingcompany.repository.PersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * ADDED: This method handles the login logic securely with debugging messages.
     * @param email The user's email.
     * @param rawPassword The plain-text password from the form.
     * @return An Optional containing the Person if credentials are valid.
     */
    public Optional<Person> login(String email, String rawPassword) {
        // --- Start of debugging code ---
        System.out.println("\n--- INICIANDO INTENTO DE LOGIN ---");
        System.out.println("Buscando usuario con email: " + email);

        Optional<Person> optionalPerson = personRepository.findByEmail(email);

        if (optionalPerson.isEmpty()) {
            System.out.println(">>> RESULTADO: Usuario NO encontrado en la base de datos.");
            System.out.println("------------------------------------\n");
            return Optional.empty();
        }

        Person person = optionalPerson.get();
        System.out.println(">>> RESULTADO: Usuario encontrado: " + person.getFirstName() + " " + person.getLastName());
        System.out.println("Rol del usuario: " + person.getPersonRole());
        System.out.println("Contraseña recibida del formulario: " + rawPassword);
        System.out.println("Contraseña en la Base de Datos (encriptada): " + person.getPassword());

        boolean passwordMatches = passwordEncoder.matches(rawPassword, person.getPassword());
        System.out.println(">>> ¿Las contraseñas coinciden?: " + passwordMatches);

        if (person.getPersonRole() == PersonRole.CONSULTANT && passwordMatches) {
            System.out.println(">>> LOGIN EXITOSO!");
            System.out.println("------------------------------------\n");
            return Optional.of(person);
        } else {
            System.out.println(">>> LOGIN FALLIDO: El rol no es CONSULTANT o la contraseña no coincide.");
            System.out.println("------------------------------------\n");
            return Optional.empty();
        }
        // --- End of debugging code ---
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