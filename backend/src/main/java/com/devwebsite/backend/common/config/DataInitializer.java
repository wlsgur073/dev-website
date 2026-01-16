package com.devwebsite.backend.common.config;

import com.devwebsite.backend.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Profile("dev")
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> updateSeedPasswords();
    }

    @Transactional
    public void updateSeedPasswords() {
        // Update admin password: admin@example.com / admin123
        userRepository.findByEmail("admin@example.com").ifPresent(user -> {
            user.updatePassword(passwordEncoder.encode("admin123"));
            userRepository.saveAndFlush(user);
            log.info("Updated admin password hash");
        });

        // Update user password: user@example.com / user123
        userRepository.findByEmail("user@example.com").ifPresent(user -> {
            user.updatePassword(passwordEncoder.encode("user123"));
            userRepository.saveAndFlush(user);
            log.info("Updated user password hash");
        });
    }
}
