package dev.juanito.studysync.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponseDto {

    private String token;
    private String message;

    // Info del usuario para que el frontend no tenga que hacer otra petición
    private Long userId;
    private String email;
    private String name;

    // Login exitoso sin user info (legacy)
    public AuthResponseDto(String token) {
        this.token = token;
        this.message = "Inicio de sesión exitoso";
    }

    // Login exitoso con datos del usuario
    public AuthResponseDto(String token, UserResponseDto user) {
        this.token = token;
        this.message = "Inicio de sesión exitoso";
        this.userId = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
    }

    // Error de autenticación (credenciales inválidas)
    public static AuthResponseDto error(String message) {
        AuthResponseDto dto = new AuthResponseDto();
        dto.message = message;
        return dto;
    }
}
