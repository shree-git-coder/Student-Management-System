package com.example.studentManagementSystem.config;

import com.example.studentManagementSystem.model.Users;
import com.example.studentManagementSystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner loadSampleData(UserRepository userRepository , PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsername("Admin")) {
                Users users = new Users();
                users.setUsername("Admin");
                users.setPassword( passwordEncoder.encode("admin@123"));
                users.setActive(true);
                userRepository.save(users);
            }
        };
    }
}


