package dev.juanito.studysync.service;

import dev.juanito.studysync.dto.UserRegistrationDto;
import dev.juanito.studysync.dto.UserResponseDto;
import dev.juanito.studysync.dto.UserUpdateDto;
import dev.juanito.studysync.model.User;

// An interface has been created to have a better single tone good practices approach
public interface UserService {

    // The methods are public by default so the UserController can wacht them
    UserResponseDto registerUser(UserRegistrationDto userRegistrationDto);

    UserResponseDto findUserById(Long id);

    void deleteUserById(Long id);

    UserResponseDto updatedUserById(Long id, UserUpdateDto userUpdateDto);

    User findUserByEmail(String email);

}