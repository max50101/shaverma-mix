package com.example.auth_server.controller;


import com.example.auth_server.repository.UserRepository;
import com.example.auth_server.repository.entity.RegistrationForm;
import com.example.auth_server.repository.entity.User;
import com.example.auth_server.service.UserMessageService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserMessageService userMessageService;
    public RegistrationController(UserRepository repository, PasswordEncoder passwordEncoder,UserMessageService userMessageService){
        this.userRepository=repository;
        this.passwordEncoder=passwordEncoder;
        this.userMessageService=userMessageService;
    }

    @GetMapping
    public String registerForm(){
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form){
        User user=form.toUser(passwordEncoder);
        userRepository.save(user);
        userMessageService.send(user);
        return "redirect:/login";
    }
}
