package org.example.themovingcompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.themovingcompany.model.enums.PersonRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// --- LOMBOK ANNOTATIONS ADDED ---
@Getter
@Setter
@NoArgsConstructor
// ---------------------------------
@Entity
@Table(name = "persons")
public class Person implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(max = 50)
    private String phoneNumber;

    @NotBlank
    @Size(max = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    private PersonRole personRole;

    private boolean archived = false;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    private String password;

    // --- MANUALLY WRITTEN GETTERS, SETTERS, AND CONSTRUCTOR HAVE BEEN DELETED ---
    // Lombok is now generating them automatically in the background.

    // --- UserDetails implemented methods (THESE MUST REMAIN) ---
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // We add the "ROLE_" prefix that Spring Security expects by convention.
        return List.of(new SimpleGrantedAuthority("ROLE_" + personRole.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}