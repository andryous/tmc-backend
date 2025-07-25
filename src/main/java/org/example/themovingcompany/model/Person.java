package org.example.themovingcompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*; //It imports everything inside the jakarta.persistence package that includes Entity, Id, GeneratedValue, GenerationType, Enumerated.
import jakarta.validation.constraints.*;
import org.example.themovingcompany.model.enums.PersonRole;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank // The First name must not be empty or null.
    @Size(max = 50) // Max lenght allowed is 50 characters.
    private String firstName;

    @NotBlank // The Last name must not be empty or null.
    @Size(max = 50)
    private String lastName;

    @NotBlank // Email must not be empty.
    @Email // Email must follow a valid format like name@example.com
    private String email;

    @NotBlank // Phone number must not be empty (allows formats like +47...).
    @Size(max = 50) // Limit the length to 50 characters.
    private String phoneNumber;

    @NotBlank // The address must not be empty.
    @Size(max = 255) // Limit the length to 255 characters.
    private String address;

    @Enumerated(EnumType.STRING)
    private PersonRole personRole; // Store the enum as a readable String (e.g., "CUSTOMER", "CONSULTANT") instead of a number (0, 1).
    private boolean archived = false; // Indicates if the person is archived (soft delete)


    //One person can have many orders.
    @JsonIgnore // Prevent infinite loop when returning customer in JSON
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL) // Orders placed by this person as customer
    private List<Order> orders = new ArrayList<>();

    // Getters and setters (required for Spring to map JSON to this object)

    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PersonRole getPersonRole() {
        return personRole;
    }

    public void setPersonRole(PersonRole personRole) {
        this.personRole = personRole;
    }



    private String password;

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }


    // Getter and setter for the list of orders (@OneToMany relationship)
    // These methods manage the list of orders linked to this person.

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}