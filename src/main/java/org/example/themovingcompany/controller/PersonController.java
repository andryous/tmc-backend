// File: src/main/java/org/example/themovingcompany/controller/PersonController.java
package org.example.themovingcompany.controller;

import jakarta.validation.Valid;
import org.example.themovingcompany.model.Person;
import org.example.themovingcompany.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // POST /api/persons/login --> Authenticates a consultant by email and password
    @PostMapping("/login")
    public ResponseEntity<?> loginConsultant(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        Optional<Person> personOptional = personService.login(email, password);

        // CHANGED: Rewrote the return statement to avoid the type conflict.
        if (personOptional.isPresent()) {
            // If present, return the Person object with a 200 OK status.
            return ResponseEntity.ok(personOptional.get());
        } else {
            // If not present, return a String message with a 401 Unauthorized status.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    // --- Other methods remain the same ---

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/by-role/{role}")
    public List<Person> getPersonsByRole(@PathVariable String role) {
        return personService.getPersonsByRole(role);
    }

    @GetMapping("/by-role/CUSTOMER/archived")
    public List<Person> getArchivedCustomers() {
        return personService.getArchivedCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Person createPerson(@Valid @RequestBody Person person) {
        return personService.createPerson(person);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @Valid @RequestBody Person updatedPerson) {
        return personService.updatePerson(id, updatedPerson)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Person> patchPerson(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        try {
            Person updatedPerson = personService.patchPerson(id, updates);
            return ResponseEntity.ok(updatedPerson);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}