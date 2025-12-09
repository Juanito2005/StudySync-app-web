package dev.juanito.studysync.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.juanito.studysync.dto.UserRegistrationDto;
import dev.juanito.studysync.dto.UserResponseDto;
import dev.juanito.studysync.dto.UserUpdateDto;
import dev.juanito.studysync.service.UserService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        UserResponseDto newUser = userService.registerUser(userRegistrationDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserByItsId(@PathVariable Long id) {
        UserResponseDto user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserByItsId(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> updateUserByItsId(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
        UserResponseDto userUpdated = userService.updatedUserById(id, userUpdateDto);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }
}
