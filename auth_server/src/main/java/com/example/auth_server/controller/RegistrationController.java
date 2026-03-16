package com.example.auth_server.controller;


import com.example.auth_server.repository.UserRepository;
import com.example.auth_server.repository.entity.RegistrationForm;
import com.example.auth_server.repository.entity.User;
import com.example.auth_server.service.UserMessageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserMessageService userMessageService;
    private UserDetailsService userDetailsService;
    public RegistrationController(UserRepository repository, PasswordEncoder passwordEncoder, UserMessageService userMessageService, UserDetailsService userDetailsService){
        this.userRepository=repository;
        this.passwordEncoder=passwordEncoder;
        this.userMessageService=userMessageService;
        this.userDetailsService=userDetailsService;
    }

    @GetMapping
    public String registerForm(){
        return "registration";
    }

    @PostMapping
    public void processRegistration(@ModelAttribute  RegistrationForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user=form.toUser(passwordEncoder);
        userRepository.save(user);
        userMessageService.send(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);

        new HttpSessionSecurityContextRepository()
                .saveContext(context, request, response);
        var savedRequest = new HttpSessionRequestCache().getRequest(request, response);

        if (savedRequest != null && savedRequest.getRedirectUrl().contains("/oauth2/authorize")) {
            response.sendRedirect(savedRequest.getRedirectUrl());
            return;
        }

        response.sendRedirect("/login");
    }
}
