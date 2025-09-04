package com.worknest.app;

import com.worknest.app.model.Role;
import com.worknest.app.model.User;
import com.worknest.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        /*
        // --- Logic is commented out to allow for manual user creation ---

        // Create a default ADMIN user if one doesn't exist
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRole(Role.ROLE_ADMIN);
            userRepository.save(admin);
            System.out.println(">>> Default ADMIN user creation is disabled.");
        }

        // Create a default USER if one doesn't exist
        if (userRepository.findByUsername("user").isEmpty()) {
            User regularUser = new User();
            regularUser.setUsername("user");
            regularUser.setPassword(passwordEncoder.encode("password"));
            regularUser.setRole(Role.ROLE_USER);
            userRepository.save(regularUser);
            System.out.println(">>> Default USER user creation is disabled.");
        }
        */
        System.out.println(">>> DataInitializer is disabled. Please register users manually via the frontend.");
    }
}

