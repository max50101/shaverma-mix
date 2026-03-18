package com.example.taco_cloud_client.config;

import com.example.taco_cloud_client.component.RestTemplateFactory;
import com.example.taco_cloud_client.repository.IngredientRepository;
import com.example.taco_cloud_client.repository.OrderRepository;
import com.example.taco_cloud_client.repository.UserRepository;
import com.example.taco_cloud_client.repository.impl.RestIngredientRepository;
import com.example.taco_cloud_client.repository.impl.RestOrderRepository;
import com.example.taco_cloud_client.repository.impl.RestUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.context.annotation.RequestScope;


@Configuration
public class ClientConfig {



    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/register").permitAll()
                        .requestMatchers("/design", "/orders").hasAuthority("SCOPE_writeOrders")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/taco-admin-client")
                )
                .oauth2Client(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    @RequestScope
    public IngredientRepository ingredientService(RestTemplateFactory restTemplateFactory,
                                                  @Value("${api.base-url}") String apiBaseUrl) {
        return new RestIngredientRepository(restTemplateFactory.create(),apiBaseUrl);
    }

    @Bean
    @RequestScope
    public OrderRepository orderService(RestTemplateFactory restTemplateFactory,
                                        @Value("${api.base-url}") String apiBaseUrl) {
        return new RestOrderRepository(restTemplateFactory.create(),apiBaseUrl);
    }

    @Bean
    @RequestScope
    public UserRepository userService(RestTemplateFactory restTemplateFactory, @Value("${api.base-url}") String apiBaseUrl){
        return new RestUserRepository(restTemplateFactory.create(),apiBaseUrl);
    }




}
