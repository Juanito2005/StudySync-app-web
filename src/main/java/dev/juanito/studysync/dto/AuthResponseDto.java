package dev.juanito.studysync.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {

    private String token;
    private String message;

    public AuthResponseDto(String token) {
        this.token = token;
        this.message = "Loggin successful";
    }
}
