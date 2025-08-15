// File: src/main/java/org/example/themovingcompany/model/PersonResponseDTO.java
package org.example.themovingcompany.model;

// A simplified Person object for API responses.
public class PersonResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;

    public PersonResponseDTO(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}