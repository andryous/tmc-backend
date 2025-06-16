package org.example.themovingcompany.controller;


import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProfileController {

    private final Environment env;

    // Inject Spring's Environment to read active profiles
    public ProfileController(Environment env) {
        this.env = env;
    }

    // GET /active-profile --> Returns the currently active Spring profile.
    @GetMapping("/active-profile")
    public Map<String, String> getActiveProfile() {
        Map<String, String> response = new HashMap<>();
        String[] profiles = env.getActiveProfiles();

        // If no profile is active, fallback to "default"
        String activeProfile = profiles.length > 0 ? profiles[0] : "default";

        response.put("activeProfile", activeProfile);
        return response;
    }



}
