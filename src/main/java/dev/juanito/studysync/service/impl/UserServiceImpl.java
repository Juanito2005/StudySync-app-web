package dev.juanito.studysync.service.impl;

import dev.juanito.studysync.model.User;

import org.springframework.stereotype.Service;

import dev.juanito.studysync.dto.UserRegistrationDto;
import dev.juanito.studysync.dto.UserUpdateDto;
import dev.juanito.studysync.exception.EmailAlreadyExistException;
import dev.juanito.studysync.exception.UserEmailNotFoundException;
import dev.juanito.studysync.exception.UserIdNotFoundException;
import dev.juanito.studysync.repository.UserRepository;
import dev.juanito.studysync.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    // Dependency injection so this class isn't accopled to the repository
    // @AutoWired is not recommended
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* Returning a user instead nothing (void) is key because we could know important things such as the Id or date that
    The use ws created */
    @Override
    public User registerUser(UserRegistrationDto userRegistrationDto) {
        checkIfEmailExist(userRegistrationDto.getEmail());
        User user = new User();
        user.setName(userRegistrationDto.getName());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(userRegistrationDto.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserIdNotFoundException("The user Id has not been found"));
    }

    @Override
    public void deleteUserById(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    @Override
    public User updatedUserById(Long id, UserUpdateDto userUpdateDto) {
        User user = findUserById(id);
        if (userUpdateDto.getEmail() != null) {
            checkIfEmailExist(userUpdateDto.getEmail());
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getName() != null) {
            user.setName(userUpdateDto.getName());
        }
        return userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserEmailNotFoundException("The email doesn't exists"));
    }

    public void checkIfEmailExist(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistException("A user with this email already exist");
        }
    }


}
