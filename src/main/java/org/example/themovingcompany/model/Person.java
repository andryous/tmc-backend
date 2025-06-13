package org.example.themovingcompany.model;
import jakarta.persistence.*; //It imports everything inside the jakarta.persistence package that includes Entity, Id, GeneratedValue, GenerationType, Enumerated.
import jakarta.validation.constraints.*;



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








}
