package org.example.themovingcompany.controller;


import jakarta.validation.Valid;
import org.example.themovingcompany.model.Person;
import org.example.themovingcompany.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController                          //Exposes endpoints as REST.
@RequestMapping("/api/persons")          //Base path for this resource.
public class PersonController {
    private final PersonService personService;

    // Constructor injection of the service layer.
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    //GET /api/persons --> returns the full list.
    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    //GET /api/persons/{id}  --> returns a single person or 404
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id)
                .map(ResponseEntity::ok)          //200 Ok if found.
                .orElseGet(() -> ResponseEntity.notFound().build()); // 404 if not.
    }

    // POST /api/persons --> creates a new person
    @PostMapping
    public Person createPerson(@Valid @RequestBody Person person) {
        return personService.createPerson(person);
    }

    // DELETE /api/persons/{id}  --> Deletes a person.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)   // 204 No Content.
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }

    // PUT /api/persons/{id} --> Replace an existing person completely.
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @Valid @RequestBody Person updatedPerson) {

        // Call the service to update the person with new data.
        return personService.updatePerson(id, updatedPerson)
                .map(ResponseEntity::ok) // If update is successful, return 200 OK with the updated person.
                .orElseGet(() -> ResponseEntity.notFound().build()); // If person not found, return 404 Not Found.
    }

    // PATCH /api/persons/{id} --> Partially update an existing person.
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