package com.example.auth_server.config;

import com.example.auth_server.repository.UserRepository;
import com.example.auth_server.repository.entity.User;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoaderConfig {
    @Bean
    public ApplicationRunner dataLoader(UserRepository userRepository,
                                        PasswordEncoder passwordEncoder){
        return args -> {
            userRepository.save(
                    new User("habuma",
                            passwordEncoder.encode("password"), "ROLE_ADMIN"));
            userRepository.save(
                    new User("tacochef",
                            passwordEncoder.encode("password"), "ROLE_ADMIN"));
        };
    }
}
