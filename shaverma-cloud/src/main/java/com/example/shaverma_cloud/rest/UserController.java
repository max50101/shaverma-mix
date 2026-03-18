package com.example.shaverma_cloud.rest;
import com.example.shaverma_cloud.dto.response.UserResponse;
import com.example.shaverma_cloud.model.User;
import com.example.shaverma_cloud.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserRepository userRepository;
    public UserController(UserRepository repository){
        this.userRepository=repository;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal Jwt jwt){
        String userId=jwt.getSubject();
        return userRepository.findByUsername(userId).map(user->ResponseEntity.ok(new UserResponse(user.getUsername(),user.getFullname()
                ,user.getStreet(),user.getCity(),user.getState()
                ,user.getZip(),user.getPhoneNumber())))
                .orElse(ResponseEntity.notFound().build());
    }
}
