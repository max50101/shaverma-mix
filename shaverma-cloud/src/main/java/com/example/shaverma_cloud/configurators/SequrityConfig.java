package com.example.shaverma_cloud.configurators;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SequrityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain apiChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"api/ingredients")
                        .hasAuthority("SCOPE_writeIngredients")
                        .requestMatchers(HttpMethod.DELETE,"api/ingredients")
                        .hasAuthority("SCOPE_deleteIngredients")
                        .requestMatchers(HttpMethod.POST,"api/v1/orders")
                        .hasAuthority("SCOPE_writeOrders")
                         .requestMatchers(HttpMethod.DELETE,"api/v1/orders")
                         .hasAuthority("SCOPE_deleteOrders")
                                .requestMatchers(HttpMethod.GET,"api/v1/users").hasAuthority("SCOPE_getUsers")
                        .anyRequest().permitAll() // или authent
                                    // icated(), если API надо защищать
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(withDefaults())
                )
                .formLogin(form -> form.disable()) // <- ключевое: без редиректа на /login
                .httpBasic(withDefaults())         // удобно для curl, если включишь authenticated()
                .build();
    }


    }
