package dev.juanito.studysync.service;

import java.util.Optional;

import dev.juanito.studysync.dto.UserRegistrationDto;
import dev.juanito.studysync.dto.UserUpdateDto;
import dev.juanito.studysync.model.User;

// An interface has been created to have a better single tone good practices approach
public interface UserService {

    // The methods are public by default so the UserController can wacht them
    User registerUser(UserRegistrationDto userRegistrationDto);

    Optional<User> findUserById(Long id);

    void deleteUserById(Long id);

    User updatedUserById(Long id, UserUpdateDto userUpdateDto);

}