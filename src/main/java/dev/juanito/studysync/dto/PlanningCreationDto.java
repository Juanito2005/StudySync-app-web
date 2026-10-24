package dev.juanito.studysync.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlanningCreationDto {

    @NotNull(message = "Las horas planeadas son obligatorias, ¡no me dejes adivinando!")
    @Positive(message = "Las horas planeadas deben ser un número positivo, parce")
    private Double plannedHours;

    @NotNull(message = "La fecha es obligatoria, ¿cuándo vas a estudiar?")
    private LocalDateTime date;

    @NotNull(message = "La materia es obligatoria, ¿de qué vas a estudiar pues?")
    private Long subjectId;
}
