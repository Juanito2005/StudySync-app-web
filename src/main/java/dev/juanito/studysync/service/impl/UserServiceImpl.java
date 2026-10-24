package dev.juanito.studysync.service.impl;

import dev.juanito.studysync.dto.UserRegistrationDto;
import dev.juanito.studysync.dto.UserResponseDto;
import dev.juanito.studysync.dto.UserUpdateDto;
import dev.juanito.studysync.exception.EmailAlreadyExistException;
import dev.juanito.studysync.exception.UserEmailNotFoundException;
import dev.juanito.studysync.exception.UserIdNotFoundException;
import dev.juanito.studysync.model.User;
import dev.juanito.studysync.repository.UserRepository;
import dev.juanito.studysync.security.AuthenticationHelper;
import dev.juanito.studysync.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationHelper authenticationHelper;

    public UserServiceImpl(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AuthenticationHelper authenticationHelper
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationHelper = authenticationHelper;
    }

    private UserResponseDto toUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    @Override
    public UserResponseDto registerUser(
        UserRegistrationDto userRegistrationDto
    ) {
        checkIfEmailExist(userRegistrationDto.getEmail());
        User user = new User();
        user.setName(userRegistrationDto.getName());
        user.setEmail(userRegistrationDto.getEmail());
        String hashedPassword = passwordEncoder.encode(
            userRegistrationDto.getPassword()
        );
        user.setPassword(hashedPassword);
        User savedUser = userRepository.save(user);
        return toUserResponseDto(savedUser);
    }

    @Override
    public UserResponseDto findUserById(Long id) {
        User authenticatedUser = authenticationHelper.getAuthenticatedUser();
        if (!authenticatedUser.getId().equals(id)) {
            throw new UserIdNotFoundException(
                "Acceso denegado: solo puedes ver tu propio perfil"
            );
        }
        User user = userRepository
            .findById(id)
            .orElseThrow(() ->
                new UserIdNotFoundException("El usuario no ha sido encontrado")
            );
        return toUserResponseDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        User authenticatedUser = authenticationHelper.getAuthenticatedUser();
        if (!authenticatedUser.getId().equals(id)) {
            throw new UserIdNotFoundException(
                "Acceso denegado: solo puedes eliminar tu propio perfil"
            );
        }
        userRepository.delete(authenticatedUser);
    }

    @Override
    public UserResponseDto updatedUserById(
        Long id,
        UserUpdateDto userUpdateDto
    ) {
        User authenticatedUser = authenticationHelper.getAuthenticatedUser();
        if (!authenticatedUser.getId().equals(id)) {
            throw new UserIdNotFoundException(
                "Acceso denegado: solo puedes actualizar tu propio perfil"
            );
        }
        if (userUpdateDto.getEmail() != null) {
            checkIfEmailExist(userUpdateDto.getEmail());
            authenticatedUser.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getName() != null) {
            authenticatedUser.setName(userUpdateDto.getName());
        }
        User updatedUser = userRepository.save(authenticatedUser);
        return toUserResponseDto(updatedUser);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository
            .findByEmail(email)
            .orElseThrow(() ->
                new UserEmailNotFoundException(
                    "El correo no existe en el sistema"
                )
            );
    }

    public void checkIfEmailExist(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistException(
                "Ya existe un usuario con este correo"
            );
        }
    }
}
