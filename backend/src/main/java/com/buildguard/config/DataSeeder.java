package com.buildguard.config;

import com.buildguard.model.User;
import com.buildguard.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("admin@buildguard.com").isEmpty()) {
                User user = new User();
                user.setEmail("admin@buildguard.com");
                user.setPassword(passwordEncoder.encode("admin123"));
                user.setRole("ADMIN");
                userRepository.save(user);
            }
        };
    }
}