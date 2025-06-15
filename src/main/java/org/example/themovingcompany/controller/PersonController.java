package org.example.themovingcompany.controller;

import jakarta.validation.Valid;
import org.example.themovingcompany.model.Person;
import org.example.themovingcompany.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ResponseStatus(HttpStatus.NO_CONTENT)   // 204 No Content
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }


}
