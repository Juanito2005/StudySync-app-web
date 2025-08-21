package dev.juanito.studysync.service.impl;

import dev.juanito.studysync.model.User;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.juanito.studysync.dto.UserRegistrationDto;
import dev.juanito.studysync.exception.EmailAlreadyExistException;
import dev.juanito.studysync.repository.UserRepository;
import dev.juanito.studysync.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    //Dependency injection so this class isn't accopled to the repository
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* Returning a user instead nothing (void) is key because we could know important things such as the Id or date that
    The use ws created */
    @Override
    public User registerUser(UserRegistrationDto userRegistrationDto) {
        /*
         * Dejemos esta exception pendiente, podríamos separarla para después reutilizarla
         * Y seguir el principio Single Responsability Principle
         */
        if (userRepository.findByEmail(userRegistrationDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistException("A user with this email already exist");
        }
        User user = new User();
        user.setName(userRegistrationDto.getName());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(userRegistrationDto.getPassword());
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        if (userRepository.findById(id).) {
            
        }
    }

    
    // @Override
    // public Optional<User> findUserById(Long id) {
        //     // Here you will write the logic to find a user by their ID.
    //     // 1. Use the userRepository to find the user.
    //     return Optional.empty();
    // }

    // @Override
    // public void deleteUserById(Long id) {
        //     // Here you will write the logic to delete a user by their ID.
        //     // 1. Check if the user exists.
    //     // 2. If they exist, delete them using the userRepository.
    // }

    // @Override
    // public User updatedUserById(Long id, UserRegistrationDto userRegistrationDto) {
        //     // Here you will write the logic to update a user.
        //     // 1. Find the existing user using their ID.
        //     // 2. Update the user's information from the DTO.
        //     // 3. Save the updated user in the database.
    //     // 4. Return the updated User entity.
    //     return null;
    // }
}

// @Override
// public Optional<User> findUserByEmail(String email) {}