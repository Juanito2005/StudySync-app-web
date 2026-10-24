package dev.juanito.studysync.controller;

import dev.juanito.studysync.dto.AuthResponseDto;
import dev.juanito.studysync.dto.UserLoginDto;
import dev.juanito.studysync.dto.UserResponseDto;
import dev.juanito.studysync.exception.UserEmailNotFoundException;
import dev.juanito.studysync.model.User;
import dev.juanito.studysync.security.jwt.JwtService;
import dev.juanito.studysync.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
        UserService userService,
        JwtService jwtService,
        PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> isValidCredentials(
        @Valid @RequestBody UserLoginDto userLoginDto
    ) {
        try {
            User user = userService.findUserByEmail(userLoginDto.getEmail());
            if (
                passwordEncoder.matches(
                    userLoginDto.getPassword(),
                    user.getPassword()
                )
            ) {
                String token = jwtService.createToken(user);
                UserResponseDto userDto = new UserResponseDto(
                    user.getId(),
                    user.getName(),
                    user.getEmail()
                );
                return new ResponseEntity<>(
                    new AuthResponseDto(token, userDto),
                    HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                    AuthResponseDto.error("Credenciales incorrectas"),
                    HttpStatus.UNAUTHORIZED
                );
            }
        } catch (UserEmailNotFoundException ex) {
            return new ResponseEntity<>(
                AuthResponseDto.error("Credenciales incorrectas"),
                HttpStatus.UNAUTHORIZED
            );
        }
    }
}
