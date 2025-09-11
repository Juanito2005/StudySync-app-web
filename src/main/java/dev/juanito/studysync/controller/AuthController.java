package dev.juanito.studysync.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.juanito.studysync.dto.UserLoginDto;
import dev.juanito.studysync.exception.UserEmailNotFoundException;
import dev.juanito.studysync.model.User;
import dev.juanito.studysync.security.jwt.JwtService;
import dev.juanito.studysync.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> isValidCredentials(@Valid @RequestBody UserLoginDto userLoginDto) {
        try {
            User user = userService.findUserByEmail(userLoginDto.getEmail());

            if (user.getPassword().equals(userLoginDto.getPassword())) {
                String token = jwtService.createToken(user);
                return new ResponseEntity<>(token, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
            }

        } catch (UserEmailNotFoundException ex) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }
}
