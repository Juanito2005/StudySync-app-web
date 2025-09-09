package dev.juanito.studysync.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectCreationDto {

    @NotBlank(message = "The name cannot be blank")
    public String name;

    @NotBlank(message = "The color cannot be blank")
    public String color;
}
