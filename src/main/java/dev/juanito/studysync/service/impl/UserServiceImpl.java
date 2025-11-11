package dev.juanito.studysync.service.impl;

import dev.juanito.studysync.model.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.juanito.studysync.dto.UserRegistrationDto;
import dev.juanito.studysync.dto.UserUpdateDto;
import dev.juanito.studysync.exception.EmailAlreadyExistException;
import dev.juanito.studysync.exception.UserEmailNotFoundException;
import dev.juanito.studysync.exception.UserIdNotFoundException;
import dev.juanito.studysync.repository.UserRepository;
import dev.juanito.studysync.security.AuthenticationHelper;
import dev.juanito.studysync.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    // Dependency injection so this class isn't accopled to the repository
    // @AutoWired is not recommended
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationHelper authenticationHelper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationHelper authenticationHelper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationHelper = authenticationHelper;
    }

    /* Returning a user instead nothing (void) is key because we could know important things such as the Id or date from the
    created user*/
    @Override
    public User registerUser(UserRegistrationDto userRegistrationDto) {
        checkIfEmailExist(userRegistrationDto.getEmail());
        User user = new User();
        user.setName(userRegistrationDto.getName());
        user.setEmail(userRegistrationDto.getEmail());
        String hashedPassword = passwordEncoder.encode(userRegistrationDto.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        User authenticatedUser = authenticationHelper.getAuthenticatedUser();
        if (!authenticatedUser.getId().equals(id)) {
            throw new UserIdNotFoundException("Access denied: You can only view your own profile");
        }
        return userRepository.findById(id).orElseThrow(() -> new UserIdNotFoundException("The user Id has not been found"));
    }

    @Override
    public void deleteUserById(Long id) {
        User authenticatedUser = authenticationHelper.getAuthenticatedUser();
        if (!authenticatedUser.getId().equals(id)) {
            throw new UserIdNotFoundException("Access denied: You can only delete your own profile");
        }
        userRepository.delete(authenticatedUser);
    }

    @Override
    public User updatedUserById(Long id, UserUpdateDto userUpdateDto) {
        User authenticatedUser = authenticationHelper.getAuthenticatedUser();
        if (!authenticatedUser.getId().equals(id)) {
            throw new UserIdNotFoundException("Access denied: You can only update your own profile");
        }
        if (userUpdateDto.getEmail() != null) {
            checkIfEmailExist(userUpdateDto.getEmail());
            authenticatedUser.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getName() != null) {
            authenticatedUser.setName(userUpdateDto.getName());
        }
        return userRepository.save(authenticatedUser);
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
