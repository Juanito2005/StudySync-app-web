package dev.juanito.studysync.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import dev.juanito.studysync.exception.UserIdNotFoundException;
import dev.juanito.studysync.model.User;
import dev.juanito.studysync.repository.UserRepository;

@Component
public class AuthenticationHelper {
    private final UserRepository userRepository;

    public AuthenticationHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Obtiene el usuario autenticado actualmente desde el SecurityContext.
     * Este método se ejecuta CADA VEZ que es llamado, garantizando que
     * siempre obtenemos el usuario correcto de la petición HTTP actual.
     *
     * @return User - El usuario autenticado
     * @throws UserIdNotFoundException si el usuario no existe en la base de datos
     */
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Es bueno comprobar que la authentication exists y es del tipo esperado
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal)) {
            throw new IllegalStateException("No valid authentication found in SecurityContext");
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return userRepository.findById(userPrincipal.getUserId())
                .orElseThrow(() -> new UserIdNotFoundException("Authenticated user with id " + userPrincipal.getUserId() + " not found in database"));
    }

    public Long getAuthenticatedUserId() {
        
    }
}
