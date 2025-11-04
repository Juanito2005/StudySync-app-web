package dev.juanito.studysync.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {

    @NotBlank(message = "The user email cannot be blank")
    private String email;

    @NotBlank(message = "The user password cannot be blank")
    private String password;
}
